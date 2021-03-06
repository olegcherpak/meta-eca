DESCRIPTION = "Packagegroup for ECA packages"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"
DEPENDS = "virtual/kernel"
PR = "r2"

inherit packagegroup

PACKAGES = "\
    packagegroup-eca \
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""

VIRTUAL-RUNTIME_dev_manager ?= "udev"
VIRTUAL-RUNTIME_init_manager ?= "sysvinit"
VIRTUAL-RUNTIME_initscripts ?= "initscripts"
VIRTUAL-RUNTIME_keymaps ?= "keymaps "

BLUEZ_PACKAGES="\
    bluez5 \
    bluez5-testtools \
"

CONNMAN_PACKAGES="\
    connman \
    connman-init-systemd \
    connman-client \
    connman-tests \
    connman-tools \
"

NFC_PACKAGES="\
    neard \
"

OFONO_PACKAGES="\
    ofono \
    ofono-tests \
    mobile-broadband-provider-info \
    usb-modeswitch \
"

UI_PACKAGES="\
    webpy \
    eca-web \
    connman-ncurses \
"

UTIL_PACKAGES="\
    curl \
    less \
    avahi-daemon \
    iproute2 \
    iproute2-tc \
    tcpdump \
    screen \
    bridge-utils \
    lsof \
    rsync \
    socat \
    links \
    iw \
    mosh \
    macchanger \
    netcat-openbsd \
    python-scapy \
    tzdata \
    bind-utils \
    usbutils \
    wireless-tools \
    ethtool \
    omping \
    trace-cmd \
    nmap \
"

# Try to install as many wlan firmwares as possible
WLAN_FIRMWARE="\
    linux-firmware-ralink \
    linux-firmware-rtl8192ce \
    linux-firmware-rtl8192cu \
    linux-firmware-rtl8192su \
    linux-firmware-sd8686 \
    linux-firmware-wl12xx \
"

# IEEE 802.15.4 needs userspace helpers
WPAN_PACKAGES="\
    wpan-tools \
"

RDEPENDS_packagegroup-eca = "\
    base-files \
    base-passwd \
    busybox \
    ${VIRTUAL-RUNTIME_initscripts} \
    ${@base_contains("MACHINE_FEATURES", "keyboard", "${VIRTUAL-RUNTIME_keymaps}", "", d)} \
    netbase \
    ${VIRTUAL-RUNTIME_init_manager} \
    ${VIRTUAL-RUNTIME_dev_manager} \
    ${VIRTUAL-RUNTIME_update-alternatives} \
    ${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
    procps \
    packagegroup-base \
    initscripts \
    systemd-compat-units \
    kernel-modules \
    linux-firmware \
    \
    ${BLUEZ_PACKAGES} \
    ${CONNMAN_PACKAGES} \
    ${NFC_PACKAGES} \
    ${OFONO_PACKAGES} \
    ${UI_PACKAGES} \
    ${UTIL_PACKAGES} \
    ${WLAN_FIRMWARE} \
    ${WPAN_PACKAGES} \
"

RRECOMMENDS_${PN} = "\
    ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
"
