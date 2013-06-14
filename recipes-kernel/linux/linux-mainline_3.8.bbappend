# find defconfig path
FILESEXTRAPATHS := ":${THISDIR}/${PN}"

# netfilter stuff is missing from beaglebone kernel
SRC_URI += " \
        file://netfilter.cfg \
"

do_configure() {
	for i in ${S}/../*.cfg; do
		echo "Adding ${i} to ${S}/.config"
		cat ${i} >> ${S}/.config
	done

	yes '' | oe_runmake oldconfig
}

