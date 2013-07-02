FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

# This patch is in upstream but not in 2.0. The patch is needed
# so that autoscan works in ConnMan
SRC_URI += "file://dbus-Register-the-AutoScan-method-call-at-the-right-place.patch"

# Remove unnecessary stuff from depends
DEPENDS := "${@oe_filter_out('wpa-supplicant-cli', '${DEPENDS}', d)}"
DEPENDS := "${@oe_filter_out('wpa-supplicant-passphrase', '${DEPENDS}', d)}"
DEPENDS := "${@oe_filter_out('wpa-supplicant-doc', '${DEPENDS}', d)}"

do_configure_append () {
	# Activate config options needed by connman and tethering
	echo "CONFIG_WPS=y" >> wpa_supplicant/.config
	echo "CONFIG_AP=y" >> wpa_supplicant/.config
	echo "CONFIG_CTRL_IFACE_DBUS_NEW=y" >> wpa_supplicant/.config
	echo "CONFIG_BGSCAN_SIMPLE=y" >> wpa_supplicant/.config
	echo "CONFIG_AUTOSCAN_EXPONENTIAL=y" >> wpa_supplicant/.config
}
