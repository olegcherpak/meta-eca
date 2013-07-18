# find defconfig path
FILESEXTRAPATHS := "${THISDIR}/${PN}"

SRC_URI += " \
	file://wlan-atheros.cfg \
	file://wlan-ralink.cfg \
	file://wlan-realtek.cfg \
	file://wlan-broadcom.cfg \
	file://wlan-zydas.cfg \
	file://wlan-marwel.cfg \
	file://wlan-ti.cfg \
        file://wlan-intel.cfg \
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
	file://usb-eth-gadget.cfg \
	file://usb-net.cfg \
	file://ipv6.cfg \
	file://nfacct.cfg \
"
