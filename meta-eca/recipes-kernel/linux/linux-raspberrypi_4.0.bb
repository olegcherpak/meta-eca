LINUX_VERSION ?= "4.0.7"

SRCREV = "151dc845f920bb8f38abc0a6fc89093a913f82f4"
SRC_URI = "\
     git://github.com/raspberrypi/linux.git;protocol=git;branch=rpi-4.0.y \
     file://0001-bcm2835-Added-spi0-for-OpenLabs-802.15.4-chip-in-rpi.patch \
     file://0002-bcm2835-Added-spi0-for-OpenLabs-802.15.4-chip-in-rpi.patch \
     file://0001-bcm2708-Added-spi0-for-OpenLabs-802.15.4-chip.patch \
     file://0002-bcm2709-Added-spi0-for-OpenLabs-802.15.4-chip.patch \
     file://wpan.cfg \
"

do_kernel_configme_append() {
	# wpan.cfg does not seem to go into .config, so append wpan.cfg into
	# defconfig instead. This is probably not the right way to do it thou.
	cat ${WORKDIR}/wpan.cfg >> ${WORKDIR}/defconfig
}

do_install_prepend() {
	if [ -n "${KERNEL_DEVICETREE}" ]; then
		mkdir -p ${B}/arch/${ARCH}/boot/dts/overlays
	fi
}

# The linux-raspberrypi.inc file is found in meta-raspberrypi layer
# which is here git://git.yoctoproject.org/meta-raspberrypi
require recipes-kernel/linux/linux-raspberrypi.inc
