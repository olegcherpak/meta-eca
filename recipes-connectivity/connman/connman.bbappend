PRINC := "${@int(PRINC) + 2}"

inherit systemd

EXTRA_OECONF += "--with-systemdunitdir=${systemd_unitdir}/system/"

SYSTEMD_AUTO_ENABLE = "enable"

SYSTEMD_PACKAGES = "${PN}"

SYSTEMD_SERVICE_${PN} = " connman.service"
SYSTEMD_SERVICE_${PN} += " ${@base_contains('DISTRO_FEATURES', 'vpn', 'connman-vpn.service', '', d)}"

do_install_append() {
	# Remove init scripts
	rm -r ${D}${sysconfdir}/init.d
}
