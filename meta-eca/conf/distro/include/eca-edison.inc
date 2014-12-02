# Some edison specific defaults
PREFERRED_PROVIDER_virtual/kernel_edison = "linux-yocto"
PREFERRED_VERSION_linux-yocto_edison = "3.10%"
PREFERRED_PROVIDER_virtual/bootloader_edison = "u-boot"
PREFERRED_VERSION_u-boot_edison = "v2014.04%"
PREFERRED_VERSION_u-boot-fw-utils_edison = "v2014.04%"
PREFERRED_VERSION_openssl_edison = "1.0.1g%"
IMAGE_ROOTFS_SIZE_edison = "473088"

# The kernel does not boot if newer gcc is used
GCCVERSION_edison = "4.8%"
SDKGCCVERSION_edison = "${GCCVERSION}"
