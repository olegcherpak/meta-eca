
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
