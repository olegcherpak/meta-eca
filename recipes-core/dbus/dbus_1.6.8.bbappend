PRINC := "${@int(PRINC) + 1}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
	rm -rf ${D}${sysconfdir}/init.d
}
