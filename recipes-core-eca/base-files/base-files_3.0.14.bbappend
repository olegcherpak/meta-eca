PRINC := "${@int(PRINC) + 1}"

do_install_append() {
	# change the default hostname
	echo eca > ${D}${sysconfdir}/hostname
}
