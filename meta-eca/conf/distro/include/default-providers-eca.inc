#
# Default virtual providers
#
PREFERRED_PROVIDER_virtual/arm-oe-linux-gnueabi-depmod ?= "kmod-cross"
PREFERRED_PROVIDER_udev ?= "systemd"
PREFERRED_PROVIDER_udev-utils ?= "systemd"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-yocto"
PREFERRED_VERSION_linux-yocto ?= "3.14%"

# Try to use as new usb-modeswitch as possible
PREFERRED_VERSION_usb-modeswitch-data ?= "20150115"
