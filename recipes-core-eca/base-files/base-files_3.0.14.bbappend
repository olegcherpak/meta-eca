PRINC := "${@int(PRINC) + 1}"

do_install_append() {
	# change the default hostname
	echo eca > ${D}${sysconfdir}/hostname

	# /run directory might be missing so just in case
	# create it here so that systemd will not complain
	install -m 755 -d ${D}/run
}
