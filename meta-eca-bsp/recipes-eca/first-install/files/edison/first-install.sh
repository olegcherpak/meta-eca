#!/bin/sh
# first install script to do post flash install

# global variable set to 1 if output is systemd journal
fi_journal_out=0

# handle argument, if first-install is called from systemd service
# arg1 is "systemd-service"
if [ "$1" == "systemd-service" ]; then fi_journal_out=1; fi;

#echo function to output to journal system or in colored terminal
#arg $1 message
#arg $2 log level
fi_echo () {
    lg_lvl=${2:-"log"}
    msg_prefix=""
    msg_suffix=""
    case "$lg_lvl" in
        log) if [ $fi_journal_out -eq 1 ]; then msg_prefix="<5>"; else msg_prefix="\033[1m"; msg_suffix="\033[0m"; fi;;
        err) if [ $fi_journal_out -eq 1 ]; then msg_prefix="<1>"; else msg_prefix="\033[31;40m\033[1m"; msg_suffix="\033[0m"; fi;;
    esac
    printf "${msg_prefix}${1}${msg_suffix}\n"
}

# set_retry_count to failure file
# arg $1 new retry count
set_retry_count () {
    fw_setenv first_install_retry $1
}

# get_retry_count from failure from bootloader
get_retry_count () {
    retry_count=$(fw_printenv first_install_retry | tr -d "first_install_retry=")
    [ -z $retry_count ] && { set_retry_count 0; retry_count=0;}
    return $retry_count
}

# exit first_install by rebooting and handling the failure by setting
# the firmware target according to failure or success
# on failure increment fail count and reboot
# on success reboot in multi-user target
# arg $1 exit code
exit_first_install () {
    if [ $1 -eq 0 ]; then
        # reset failure count
        set_retry_count 0
        # update firmware target
        # next reboot will be on multi-user target
        fw_setenv bootargs_target multi-user
    fi

    fi_echo "Rebooting...."
    # dump journal to log file
    journalctl -u first-install -o short-iso >> /first-install.log
    reboot
}

# continue normal flow or exit on error code
# arg $1 : return code to check
# arg $2 : string resuming the action
fi_assert () {
    if [ $1 -ne 0 ]; then
        fi_echo "${2} : Failed ret($1)" err;
        exit_first_install $1;
    else
        fi_echo "${2} : Success";
    fi
}

factory_partition () {
    mkdir -p /factory
    mount /dev/disk/by-partlabel/factory /factory
    # test can fail if done during manufacturing
    if [ $? -ne 0 ];
    then
        mkfs.ext4 /dev/disk/by-partlabel/factory
        mount /dev/disk/by-partlabel/factory /factory
        echo "00:11:22:33:55:66" > /factory/bluetooth_address
        echo "VSPPYWWDXXXXXNNN" > /factory/serial_number
    fi
}

# generate sshd keys
sshd_init () {
    rm -rf /etc/ssh/*key*
    systemctl start sshdgenkeys
}

# script main part

# print to journal the current retry count
get_retry_count
retry_count=$?
set_retry_count $((${retry_count} + 1))
fi_echo "Starting First Install (try: ${retry_count})"

# format partition home to ext4
mkfs.ext4 -m0 /dev/disk/by-partlabel/home
fi_assert $? "Formatting home partition"

# backup initial /home/root directory
cp -R /home/root /tmp
fi_assert $? "Backup home/root contents of rootfs"

# mount home partition on /home
mount /dev/disk/by-partlabel/home /home
fi_assert $? "Mount /home partition"

# copy back contents to /home
mv /tmp/root /home
fi_assert $? "Restore home/root contents on new /home partition"

# create a fat32 primary partition on all available space
echo -ne "n\np\n1\n\n\nt\nb\np\nw\n" | fdisk /dev/disk/by-partlabel/update

# silent error code for now because fdisk failed to reread MBR correctly
# MBR is correct but fdisk understand it as the main system MBR, which is
# not the case.
fi_assert 0 "Formatting update partition Step 1"

# create a loop device on update disk
losetup -o 8192 /dev/loop0 /dev/disk/by-partlabel/update
fi_assert $? "Formatting update partition Step 2"

# format update partition
mkfs.vfat /dev/loop0 -n "Edison" -F 32
fi_assert $? "Formatting update partition Step 3"

# remove loop device on update disk
losetup -d /dev/loop0
fi_assert $? "Formatting update partition Step 4 final"

# handle factory partition
factory_partition

# ssh
#sshd_init
#fi_assert $? "Generating sshd keys"

# update entry in /etc/fstab to enable auto mount
sed -i 's/#\/dev\/disk\/by-partlabel/\/dev\/disk\/by-partlabel/g' /etc/fstab
fi_assert $? "Update file system table /etc/fstab"

fi_echo "First install success"

# end main part
exit_first_install 0

