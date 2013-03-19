# find defconfig path
FILESEXTRAPATHS := "${THISDIR}/${PN}"

# Kernel configs
SRC_URI_append_beagleboard = " \
	file://beagle_qemu.cfg \
	"

SRC_URI_append_vexpressa9 = " \
	file://vexpress_a9.cfg  \
	"

SRC_URI += " \
	file://wlan-atheros-3.4.cfg \
	file://wlan-ralink.cfg \
	file://wlan-realtek-3.4.cfg \
	file://wlan-zydas.cfg \
	file://bluetooth.cfg \
	file://nokia-phonet.cfg \
	file://usb-serial.cfg \
	file://tun-device.cfg \
"

KBRANCH_vexpressa9 = "standard/beagleboard"
SRCREV_machine_vexpressa9 ?= "AUTOINC"
COMPATIBLE_MACHINE_vexpressa9 = "vexpressa9"
