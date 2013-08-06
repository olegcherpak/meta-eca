do_install_append() {
	# change the default hostname
	echo eca > ${D}${sysconfdir}/hostname

	# create /etc/issue that shows whether we are building
	# standard eca or bleeding version
	echo "Embedded Connectivity Appliance (${DISTRO})" > ${D}${sysconfdir}/issue
}
