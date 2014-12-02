########
# Settings for Galileo (quark)
#
IMAGE_FSTYPES_quark = "ext3 live"

NOISO_quark = "1"
NOHDD_quark = "1"

EXTRA_IMAGEDEPENDS_quark = "eca-grub-conf"

create_rootfs_link_postprocess() {
	DIR=$PWD
	cd ${DEPLOY_DIR_IMAGE}
	rm -f rootfs.img
	ln -s ${IMAGE_LINK_NAME}.ext3 rootfs.img
	cd $DIR
}

# root fs name should be rootfs.img as initrd expects to find that
IMAGE_POSTPROCESS_COMMAND_quark += " create_rootfs_link_postprocess; "


########
# Settings for Edison
#
IMAGE_LINGUAS_edison = " "
INITRD_edison = ""
INITRD_IMAGE_edison = ""

# This is useless stuff, but necessary for building because
# inheriting bootimg also brings syslinux in..
AUTO_SYSLINUXCFG_edison = "1"
SYSLINUX_ROOT_edison = ""
SYSLINUX_TIMEOUT_edison ?= "10"
SYSLINUX_LABELS_edison ?= "boot install"
LABELS_append_edison = " ${SYSLINUX_LABELS} "

# Specify rootfs image type
IMAGE_FSTYPES_edison = "ext4"

# Do not use legacy nor EFI BIOS
PCBIOS_edison = "0"
# Next one is needed so that eca-image-edison.hddimg link is not removed
NOISO_edison = "0"
# Create hdd image. This has to be set after including core-image otherwise
# it's overriden with "1" and this cancel creation of the boot hddimg
NOHDD_edison = "0"

inherit bootimg

DO_ROOTFS_edison = "${PN}:do_rootfs"
do_bootimg[depends] += "${DO_ROOTFS}"

