#
# Default virtual providers
#
PREFERRED_PROVIDER_virtual/arm-oe-linux-gnueabi-depmod ?= "kmod-cross"
PREFERRED_PROVIDER_udev ?= "systemd"
PREFERRED_PROVIDER_udev-utils ?= "systemd"

PREFERRED_VERSION_bluez5 ?= "5.x+git%"
PREFERRED_VERSION_libasound-module-bluez ?= "5.x+git%"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-yocto"
PREFERRED_VERSION_linux-yocto ?= "3.10%"
