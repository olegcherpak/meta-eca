########
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
RDEPENDS_packagegroup-eca_append_x86 += "\
    ${IWLWIFI} \
"

# network configuration for connman if running qemu
RDEPENDS_packagegroup-eca_append_qemuall += "\
    connman-conf \
"

########
# Packages for Edison board
RDEPENDS_packagegroup-eca_append_edison += "\
	busybox \
	u-boot \
	u-boot-fw-utils \
	base-files \
	first-install-edison \
	pciutils \
	usbutils \
	i2c-tools \
	watchdog-sample \
	eca-pwr-button-handler \
	crashlog \
	ap-mode-toggle \
"

# Wifi firmware
RDEPENDS_packagegroup-eca_append_edison += "bcm43340-fw"

# Bluetooth Firmware patch for 43340 and its patch utility
RDEPENDS_packagegroup-eca_append_edison += "bcm43340-bt"

# service daemon that listens to rfkill events and trigger FW patch download
RDEPENDS_packagegroup-eca_append_edison += "bluetooth-rfkill-event"

# Wifi driver built as a kernel module
RDEPENDS_packagegroup-eca_append_edison += "bcm43340-mod"

# Those are necessary to manually create partitions and file systems on the eMMC
RDEPENDS_packagegroup-eca_append_edison += "\
	parted \
	e2fsprogs \
	dosfstools \
"

# No PCI in Edison so iwlwifi is useless there
RDEPENDS_packagegroup-eca_remove_edison += "\
    ${IWLWIFI} \
"

########
# Packages for RaspberryPi board
RDEPENDS_packagegroup-eca_append_rpi += "\
	u-boot-rpi \
	pi-blaster \
	rpi-gpio \
	rpio \
	wiringpi \
"
