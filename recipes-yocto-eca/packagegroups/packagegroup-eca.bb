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
    bluez5-test \
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
"

UTIL_PACKAGES="\
    curl \
    less \
    avahi \
    avahi-daemon \
    iproute2 \
    tcpdump \
    screen \
    bridge-utils \
    lsof \
    rsync \
    socat \
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
    eglibc \
    linux-firmware \
    \
    ${BLUEZ_PACKAGES} \
    ${CONNMAN_PACKAGES} \
    ${NFC_PACKAGES} \
    ${OFONO_PACKAGES} \
    ${UI_PACKAGES} \
    ${UTIL_PACKAGES} \
    ${WLAN_FIRMWARE} \
"

RRECOMMENDS_${PN} = "\
    ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
"
