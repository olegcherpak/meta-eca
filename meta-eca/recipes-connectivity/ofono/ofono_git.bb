require recipes-connectivity/ofono/ofono.inc

S	= "${WORKDIR}/git"
SRCREV	= "7a6da06f275d9156687e49b823bd97e31c2cf6c4"
PV	= "1.x+git${SRCREV}"

SRC_URI = "\
	git://git.kernel.org/pub/scm/network/ofono/ofono.git;protocol=git \
	file://ofono \
"

# We want to use bluez5
DEPENDS := "${@oe_filter_out('bluez4', '${DEPENDS}', d)}"
DEPENDS += "${@base_contains('DISTRO_FEATURES', 'bluetooth','bluez5', '', d)}"

