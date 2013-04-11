require ofono.inc

S	= "${WORKDIR}/git"
SRCREV	= "${AUTOREV}"
PV	= "1.x+git${SRCREV}"
PR	= "${INC_PR}.0"

SRC_URI = "\
	git://git.kernel.org/pub/scm/network/ofono/ofono.git;protocol=git \
	file://ofono \
"

EXTRA_OECONF += "\
    --enable-test \
    ${@base_contains('DISTRO_FEATURES', 'bluetooth','--enable-bluetooth', '--disable-bluetooth', d)} \
"
