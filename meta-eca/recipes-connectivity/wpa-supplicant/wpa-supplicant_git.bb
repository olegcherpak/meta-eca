require wpa-supplicant.inc

# For P2P support we want to use a version from git
SRCREV = "6d00ab04302df257cb3092b2b31b4eac42e77569"
SRC_URI = "\
	git://w1.fi/srv/git/hostap.git \
	file://defconfig-gnutls \
	file://fix-libnl3-host-contamination.patch \
	file://p2p-dbus-group-finished-support.patch \
"

S = "${WORKDIR}/git"
PR = "r1"
PV = "2.2+git${SRCPV}"

do_configure_append () {
	# Activate config options needed by connman and tethering
	echo "CONFIG_WPS=y" >> wpa_supplicant/.config
	echo "CONFIG_WPS2=y" >> wpa_supplicant/.config
	echo "CONFIG_AP=y" >> wpa_supplicant/.config
	echo "CONFIG_CTRL_IFACE_DBUS_NEW=y" >> wpa_supplicant/.config
	echo "CONFIG_BGSCAN_SIMPLE=y" >> wpa_supplicant/.config
	echo "CONFIG_AUTOSCAN_EXPONENTIAL=y" >> wpa_supplicant/.config
	echo "CONFIG_P2P=y" >> wpa_supplicant/.config
}
