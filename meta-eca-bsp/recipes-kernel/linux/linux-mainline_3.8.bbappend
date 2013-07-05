# find defconfig path
FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

# netfilter stuff is missing from beaglebone kernel
SRC_URI_append_beaglebone += "file://netfilter.cfg"

do_configure_append_beaglebone () {
	for i in ${S}/../*.cfg; do
		echo "Adding ${i} to ${S}/.config"
		cat ${i} >> ${S}/.config
	done

	yes '' | oe_runmake oldconfig
}
