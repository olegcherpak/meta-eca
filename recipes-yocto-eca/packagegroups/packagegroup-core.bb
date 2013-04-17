DESCRIPTION = "Core packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${ECA_COREBASE}/meta-eca/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r2"

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
    bluez5 \
    bluez5-test \
    bridge-utils \
    lsof \
    linux-firmware \
"

# Try to install as many wlan firmwares as possible
RDEPENDS_packagegroup-core += "\
    linux-firmware-ralink \
    linux-firmware-rtl8192ce \
    linux-firmware-rtl8192cu \
    linux-firmware-rtl8192su \
    linux-firmware-sd8686 \
    linux-firmware-wl12xx \
"

# Add iwlwifi firmware for some Intel devices. This should probably be done
# more intelligently.
RDEPENDS_packagegroup-core += "\
    ${@base_contains("MACHINE", "nuc", "linux-firmware-iwlwifi-6000g2b-6", "", d)} \
    ${@base_contains("MACHINE", "atom-pc", "linux-firmware-iwlwifi-6000g2b-6", "", d)} \
  "

# network configuration for connman if running qemu
RDEPENDS_packagegroup-core_append_qemuall += "\
    connman-conf \
    connman-conf-systemd \
  "

RRECOMMENDS_${PN} = "\
    "
