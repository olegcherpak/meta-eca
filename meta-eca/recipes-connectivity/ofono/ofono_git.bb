require recipes-connectivity/ofono/ofono.inc

S	= "${WORKDIR}/git"
SRCREV	= "c54e4763f8f4f6239a778994cf68f077ad2b170d"
PV	= "1.x+git${SRCREV}"

SRC_URI = "\
	git://git.kernel.org/pub/scm/network/ofono/ofono.git;protocol=git \
	file://ofono \
"

# We want to use bluez5
DEPENDS := "${@oe_filter_out('bluez4', '${DEPENDS}', d)}"
DEPENDS += "${@base_contains('DISTRO_FEATURES', 'bluetooth','bluez5', '', d)}"

