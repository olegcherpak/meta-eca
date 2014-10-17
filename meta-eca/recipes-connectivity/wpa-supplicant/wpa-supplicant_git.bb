require wpa-supplicant.inc

# For P2P support we want to use a version from git
SRCREV = "3f7ac05878ba965e941f2b5b80b8cb744e63f506"
SRC_URI = "\
	git://w1.fi/srv/git/hostap.git \
	file://defconfig-gnutls \
	file://fix-libnl3-host-contamination.patch \
"

S = "${WORKDIR}/git"
PR = "r2"
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
