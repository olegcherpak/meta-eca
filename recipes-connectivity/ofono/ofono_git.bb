require ofono.inc

S	= "${WORKDIR}/git"
SRCREV	= "fc7de0f6e18e6b9cf874207cc78d9993d122f3ff"
PV	= "1.x+git${SRCREV}"
PR	= "${INC_PR}.0"

SRC_URI = "\
	git://git.kernel.org/pub/scm/network/ofono/ofono.git;protocol=git \
"

# Add git commit id to ofono version. Needed so that we know
# what ofono version we are running. The patch also requires
# that we enable maintainer mode
EXTRA_OECONF += " --enable-maintainer-mode "
SRC_URI += "\
    file://0001-build-Script-to-generate-current-git-HEAD-commit-id.patch \
    file://0002-build-Use-detailed-version-information-when-printing.patch \
"

EXTRA_OECONF += "\
    --enable-test \
    ${@base_contains('DISTRO_FEATURES', 'bluetooth','--enable-bluetooth', '--disable-bluetooth', d)} \
"
