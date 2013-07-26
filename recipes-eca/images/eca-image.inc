IMAGE_INSTALL = "packagegroup-eca \
	      ${ROOTFS_PKGMANAGE_BOOTSTRAP} \
	      ${CORE_IMAGE_EXTRA_INSTALL}"

EXTRA_IMAGE_FEATURES = "debug-tweaks package-management ssh-server-dropbear"
EXTRA_IMAGEDEPENDS += "qemu-native qemu-helper-native"

IMAGE_LINGUAS = " "

LICENSE = "MIT"

PREFERRED_PROVIDER_udev = "systemd"
PREFERRED_PROVIDER_udev-utils = "systemd"

PREFERRED_PROVIDER_jpeg = "jpeg"
PREFERRED_PROVIDER_jpeg-native = "jpeg-native"

BBMASK = "meta/recipes-core/udev.*"

inherit core-image buildhistory

IMAGE_ROOTFS_SIZE = "8192"

# remove not needed ipkg informations
#ROOTFS_POSTPROCESS_COMMAND += "remove_packaging_data_files ; "

BUILDHISTORY_COMMIT = "1"
