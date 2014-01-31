DESCRIPTION = "Packagegroup for ECA packages"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"
DEPENDS = "virtual/kernel"
PR = "r2"

inherit packagegroup

PACKAGES = "\
    packagegroup-eca \
    ${@base_contains('DISTRO_FEATURES', 'internet-of-things', 'packagegroup-iot', '', d)} \
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

THE_THING_SYSTEM="\
    steward-init \
    steward \
    tts-nodejs \
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

SUMMARY_packagegroup-iot = "Internet of Things support"
RDEPENDS_packagegroup-iot = "\
    ${THE_THING_SYSTEM} \
"

RRECOMMENDS_${PN} = "\
    ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
"
