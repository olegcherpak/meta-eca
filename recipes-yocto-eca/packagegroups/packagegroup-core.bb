DESCRIPTION = "Core packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${ECA_COREBASE}/meta-eca/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"

inherit packagegroup

PACKAGES = "\
    packagegroup-core \
"

SYSTEMD_PACKAGES="\
    busybox-syslog-systemd \
    util-linux-systemd \
    wpa-supplicant-systemd \
    dropbear-systemd \
    keymaps-systemd \
    avahi-systemd \
    connman-systemd \
"

RDEPENDS_packagegroup-core = "\
    initscripts \
    kernel-modules \
    bluez4 \
    connman \
    connman-init-systemd \
    connman-client \
    connman-tests \
    connman-tools \
    connman-plugin-tist \
    connman-plugin-vpn-openvpn \
    connman-plugin-bluetooth \
    connman-plugin-ofono \
    connman-plugin-wifi \
    openvpn \
    curl \
    eglibc \
    wpa-supplicant \
    ofono \
    ofono-tests \
    mobile-broadband-provider-info \
    usb-modeswitch \
    less \
    avahi \
    avahi-daemon \
    iproute2 \
    tcpdump \
    screen \
    pptp-linux \
    webpy \
    eca-web \
"


# xl2tpd not compiled because with kernel 3.8 says:
# fatal error: asm/linkage.h: No such file or directory
#    xl2tpd 


RDEPENDS_packagegroup-core += " ${SYSTEMD_PACKAGES} "

# For NUC device add iwlwifi firmware. This should be done more intelligently.
RDEPENDS_packagegroup-core += "\
    ${@base_contains("MACHINE", "nuc", "linux-firmware-iwlwifi-6000g2b-6", "", d)} \
  "

# network configuration for connman if running qemu
RDEPENDS_packagegroup-core += "\
    ${@base_contains("MACHINE", "qemux86", "connman-conf-systemd connman-conf", "", d)} \
    ${@base_contains("MACHINE", "qemux86-64", "connman-conf-systemd connman-conf", "", d)} \
    ${@base_contains("MACHINE", "qemuarm", "connman-conf-systemd connman-conf", "", d)} \
    ${@base_contains("MACHINE", "qemumips", "connman-conf-systemd connman-conf", "", d)} \
    ${@base_contains("MACHINE", "qemuppc", "connman-conf-systemd connman-conf", "", d)} \
  "

RRECOMMENDS_${PN} = "\
    "
