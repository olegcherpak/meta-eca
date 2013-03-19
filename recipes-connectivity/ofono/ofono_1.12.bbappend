PRINC := "${@int(PRINC) + 3}"

inherit systemd

SYSTEMD_PACKAGES = "${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd = "ofono.service"
SYSTEMD_AUTO_ENABLE = "enable"

# Don't register init scripts
INITSCRIPT_NAME = ""
INITSCRIPT_PARAMS = ""

# Remove init scripts
do_install_append() {
	case "${DISTRO_FEATURES}" in
		*systemd*) rm -rf ${D}${sysconfdir}/init.d/
	esac
}

PACKAGES += " ${PN}-systemd"
