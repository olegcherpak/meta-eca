PRINC := "${@int(PRINC) + 3}"

inherit systemd

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://neard.service"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "neard.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES_${PN} += " neard.service"

do_install_append() {
	case "${DISTRO_FEATURES}" in
	    *systemd*)
		 rm -rf ${D}${sysconfdir}/init.d/
		 install -d ${D}${systemd_unitdir}/system
		 install -m 644 ${WORKDIR}/neard.service ${D}${systemd_unitdir}/system
		 ;;
	esac
}
