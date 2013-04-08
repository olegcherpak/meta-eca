DESCRIPTION = "Core packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${ECA_COREBASE}/meta-eca/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"

inherit packagegroup

PACKAGES = "\
    packagegroup-core \
"

RDEPENDS_packagegroup-core = "\
    packagegroup-base \
    initscripts \
    kernel-modules \
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
    xl2tpd \
    webpy \
    eca-web \
    neard \
"

SYSTEMD_PACKAGES="\
    busybox-syslog-systemd \
    util-linux-systemd \
    wpa-supplicant-systemd \
    dropbear-systemd \
    keymaps-systemd \
    avahi-systemd \
"

RDEPENDS_packagegroup-core += " ${SYSTEMD_PACKAGES} "

# For NUC device add iwlwifi firmware. This should be done more intelligently.
RDEPENDS_packagegroup-core += "\
    ${@base_contains("MACHINE", "nuc", "linux-firmware-iwlwifi-6000g2b-6", "", d)} \
  "

# network configuration for connman if running qemu
RDEPENDS_packagegroup-core_append_qemuall += "\
    connman-conf \
    connman-conf-systemd \
  "

RRECOMMENDS_${PN} = "\
    "
