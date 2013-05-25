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
	file://wlan-atheros.cfg \
	file://wlan-ralink.cfg \
	file://wlan-realtek.cfg \
	file://wlan-broadcom.cfg \
	file://wlan-zydas.cfg \
	file://wlan-marwel.cfg \
	file://wlan-ti.cfg \
	file://bluetooth.cfg \
	file://nokia-phonet.cfg \
	file://usb-serial.cfg \
	file://tun-device.cfg \
	file://l2tp.cfg \
	file://rfkill.cfg \
	file://mac80211.cfg \
	file://nfc.cfg \
	file://high-speed-mobile-devices.cfg \
	file://netfilter-3.8.cfg \
"

KBRANCH_vexpressa9 = "standard/beagleboard"
SRCREV_machine_vexpressa9 ?= "AUTOINC"
COMPATIBLE_MACHINE_vexpressa9 = "vexpressa9"
