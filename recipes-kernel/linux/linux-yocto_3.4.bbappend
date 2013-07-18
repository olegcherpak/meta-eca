# find defconfig path
FILESEXTRAPATHS := "${THISDIR}/${PN}"

SRC_URI += " \
	file://wlan-atheros-3.4.cfg \
	file://wlan-ralink.cfg \
	file://wlan-realtek-3.4.cfg \
	file://wlan-zydas.cfg \
	file://bluetooth.cfg \
	file://nokia-phonet.cfg \
	file://usb-serial.cfg \
	file://tun-device.cfg \
	file://usb-eth-gadget.cfg \
	file://usb-net.cfg \
	file://rfkill.cfg \
	file://ipv6.cfg \
"
