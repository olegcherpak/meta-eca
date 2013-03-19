PRINC := "${@int(PRINC) + 2}"

do_install_append() {
	# Remove init scripts
	rm -r ${D}${sysconfdir}/init.d
}
