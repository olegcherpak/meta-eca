DESCRIPTION = "Core packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${ECA_COREBASE}/meta-eca/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r2"

inherit packagegroup

PACKAGES = "\
    packagegroup-core \
"

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
    connman-plugin-vpn-openvpn \
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
"

VPN_PACKAGES="\
    openvpn \
    pptp-linux \
    xl2tpd \
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

RDEPENDS_packagegroup-core = "\
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
    ${VPN_PACKAGES} \
    ${WLAN_FIRMWARE} \
"

def get_depends_qemu(bb, d, dep):
    val = (bb.data.getVar("MACHINEOVERRIDES", d) or "")
    if val.find("qemuall"):
        return ""
    else:
        return dep

IWLWIFI := "\
    ${@get_depends_qemu(bb, d, 'linux-firmware-iwlwifi-licence')} \
    ${@get_depends_qemu(bb, d, 'linux-firmware-iwlwifi-6000g2a-5')} \
    ${@get_depends_qemu(bb, d, 'linux-firmware-iwlwifi-6000g2b-6')} \
"

# Add iwlwifi firmware for Intel devices but not for qemu images because
# user probably does not have Intel layer defined there
RDEPENDS_packagegroup-core_append_x86 += "\
    ${IWLWIFI} \
"

# network configuration for connman if running qemu
RDEPENDS_packagegroup-core_append_qemuall += "\
    connman-conf \
    connman-conf-systemd \
"

RRECOMMENDS_${PN} = "\
"
