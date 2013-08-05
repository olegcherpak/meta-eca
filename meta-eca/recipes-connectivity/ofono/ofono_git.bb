require recipes-connectivity/ofono/ofono.inc

S	= "${WORKDIR}/git"
SRCREV	= "a96aa11bb5bb314c003f054f433f709f65c16290"
PV	= "1.x+git${SRCREV}"

SRC_URI = "\
	git://git.kernel.org/pub/scm/network/ofono/ofono.git;protocol=git \
	file://ofono \
"

# We want to use bluez5
DEPENDS := "${@oe_filter_out('bluez4', '${DEPENDS}', d)}"
DEPENDS += "${@base_contains('DISTRO_FEATURES', 'bluetooth','bluez5', '', d)}"

