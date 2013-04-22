PRINC := "${@int(PRINC) + 2}"

inherit systemd

EXTRA_OECONF += "--with-systemdunitdir=${systemd_unitdir}/system/"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "ofono.service"

do_install_append() {
	# Remove init scripts
	rm -rf ${D}${sysconfdir}/init.d
}
