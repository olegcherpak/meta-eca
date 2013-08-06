BASEFILESISSUEINSTALL = "do_install_hostname_issue"

do_install_hostname_issue() {
	# change the default hostname
	echo eca > ${D}${sysconfdir}/hostname

	# create /etc/issue that shows whether we are building
	# standard eca or bleeding version
	echo "${DISTRO_NAME} (${DISTRO})" > ${D}${sysconfdir}/issue
	echo "${DISTRO_NAME} ${DISTRO_VERSION}" > ${D}${sysconfdir}/issue.net
}
