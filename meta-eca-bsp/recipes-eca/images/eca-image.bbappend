
IMAGE_FSTYPES_clanton = "ext3 live"

NOISO_clanton = "1"
NOHDD_clanton = "1"

EXTRA_IMAGEDEPENDS_clanton = "eca-grub-conf"

# root fs name should be rootfs.img as initrd expects to find that
IMAGE_POSTPROCESS_COMMAND_clanton += "rm rootfs.img; ln -s ${IMAGE_LINK_NAME}.ext3 rootfs.img; "
