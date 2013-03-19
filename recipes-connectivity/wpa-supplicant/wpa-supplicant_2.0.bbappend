PRINC := "${@int(PRINC) + 3}"

# Remove unnecessary stuff from depends
DEPENDS := "${@oe_filter_out('wpa-supplicant-cli', '${DEPENDS}', d)}"
DEPENDS := "${@oe_filter_out('wpa-supplicant-passphrase', '${DEPENDS}', d)}"

do_configure_append () {
	# Activate config options needed by connman and tethering
	echo "CONFIG_WPS=y" >> .config
	echo "CONFIG_AP=y" >> .config
	echo "CONFIG_CTRL_IFACE_DBUS_NEW=y" >> .config
	echo "CONFIG_BGSCAN_SIMPLE=y" >> .config
}
