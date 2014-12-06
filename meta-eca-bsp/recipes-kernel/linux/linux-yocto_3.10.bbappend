# This contains settings specific to Edison board

FILESEXTRAPATHS_prepend := "${THISDIR}/edison:"

COMPATIBLE_MACHINE_edison = "edison"
LINUX_VERSION_edison = "3.10.17"
SRCREV_machine_edison = "c03195ed6e3066494e3fb4be69154a57066e845b"
SRCREV_meta_edison = "6ad20f049abd52b515a8e0a4664861cfd331f684"

SRC_URI += "file://defconfig"
SRC_URI += "file://upstream_to_edison.patch"
SRC_URI += "file://enable-early-printk-intel.patch"

KERNEL_MODULE_AUTOLOAD_edison += "g_multi bcm_bt_lpm"
KERNEL_MODULE_PROBECONF_edison += "g_multi"

module_conf_bcm4334x_edison = "options bcm4334x firmware_path=/etc/firmware/fw_bcmdhd.bin nvram_path=/etc/firmware/bcmdhd.cal"
# FIXME: file parameter should be based on partition UUID (from U-Boot) or fixed
# with label (label seems to work, but driver is probed too early)
module_conf_g_multi_edison = "options g_multi file=/dev/mmcblk0p9 stall=0 idVendor=0x8087 idProduct=0x0A9E iProduct=Edison iManufacturer=Intel"
