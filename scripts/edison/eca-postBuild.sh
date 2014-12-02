#!/bin/bash

# You need uboot-tools package in Fedora to run this script.

# You should run this script from
#   build-edison/tmp-glibc/deploy/images/edison
# directory.

top_repo_dir=$(dirname $(dirname $(dirname $(readlink -f $0))))

if [ -z "$1" ]; then
    echo "Usage: $0 <edison-src-directory-path>"
    exit 1
fi

EDISON_SRC="$1"
FLASH_DIR="$EDISON_SRC/device-software/utils/flash"

if [ ! -d "$FLASH_DIR" ]; then
    echo "Flash directory $FLASH_DIR not found."
    exit 1
fi

if [ ! -f README_-_DO_NOT_DELETE_FILES_IN_THIS_DIRECTORY.txt ]; then
    echo "Please go to build-edison/tmp-*glibc/deploy/images/edison directory and run this script from there."
    exit 1
fi

if [ ! -f eca-image-edison.ext4 ]; then
    echo "ECA image for Edison not found, please build it first."
    exit 2
fi

if [ ! -x `which mkimage` ]; then
    echo "mkimage binary from uboot-tools package is not found. Please install it."
    exit 3
fi

if [ ! -d toFlash ]; then
    mkdir toFlash
fi

function copy()
{
    FILE=$1
    if [ ! -f $FILE -a ! -d $FILE ]; then
	echo "$FILE not found."
	exit 1
    fi

    if [ -f $FILE ]; then
	cp -p $FILE toFlash/
    else
	cp -pR $FILE toFlash/
    fi
    if [ $? -ne 0 ]; then
	echo "Copy $FILE -> toFlash/ failed."
    fi
}

# Cleanup previous builds
rm -rf toFlash/*

# Some binary files from edison-src directory
copy $FLASH_DIR/filter-dfu-out.js
copy $FLASH_DIR/ota_update.cmd
for f in $FLASH_DIR/*.xml
do
    copy $f
done

# Copy IFWI
for i in $FLASH_DIR/ifwi/edison/*.bin
do
    copy $i
done

# Copy boot partition (contains kernel and ramdisk)
copy eca-image-edison.hddimg

# Copy u-boot
copy u-boot-edison.img
copy u-boot-edison.bin

# Copy u-boot environments files binary
copy u-boot-envs

# build Ifwi file for using in DFU mode
# Remove FUP footer (144 bytes) as it's not needed when we directly write to boot partitions
for ifwi in toFlash/*ifwi*.bin;
do
    dfu_ifwi_name="`basename $ifwi .bin`-dfu.bin"
    dd if=$ifwi of=toFlash/$dfu_ifwi_name bs=4194304 count=1
done

# Copy rootfs
copy eca-image-edison.ext4

# Copy flashing script
copy $top_repo_dir/scripts/edison/flashall

# Preprocess OTA script
# Compute sha1sum of each file under toFlash/ and build an array containing
# @@sha1_filename:SHA1VALUE
pth_out=toFlash/
tab_size=$(for fil in $(find $pth_out -maxdepth 1 -type f -printf "%f\n"); \
	   do sha1_hex=$(sha1sum "$pth_out$fil" | cut -c -40); \
	      echo "@@sha1_$fil:$sha1_hex" ; done ;)
# iterate the array and do tag -> value substitution in ota_update.cmd
for elem in $tab_size;
do IFS=':' read -a fld_elem <<< "$elem"; \
   sed -i "s/${fld_elem[0]}/${fld_elem[1]}/g" toFlash/ota_update.cmd; done;

# Convert OTA script to u-boot script
mkimage -a 0x10000 -T script -C none -n 'ECA updater script for Edison' \
	-d toFlash/ota_update.cmd toFlash/ota_update.scr

# Supress Preprocessed OTA script
rm -f toFlash/ota_update.cmd

# Generates a formatted list of all packages included in the image
awk '{print $1 " " $3}' eca-image-edison.manifest > toFlash/package-list.txt

echo "**** Done ***"
echo "Files ready to flash in toFlash/"
echo "Next do 'cd toFlash'"
echo "Then execute 'sudo ./flashall'"
echo "*************"
