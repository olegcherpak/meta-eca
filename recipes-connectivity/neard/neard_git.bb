require recipes-connectivity/neard/neard.inc

SRC_URI  = "git://git.kernel.org/pub/scm/network/nfc/neard.git;protocol=git \
           file://neard.service.in \
	"

S      = "${WORKDIR}/git"
SRCREV = "3365ffe98e856b87ce337c103153271e24dcef9f"
PV     = "0.x+git${SRCREV}"
PR     = "r1"


# Add git commit id to neard version. Needed so that we know
# what neard version we are running. The patch also requires
# that we enable maintainer mode
EXTRA_OECONF += " --enable-maintainer-mode "
SRC_URI += "\
    file://0001-build-Script-to-generate-current-git-HEAD-commit-id.patch \
    file://0002-build-Use-detailed-version-information-when-printing.patch \
"

EXTRA_OECONF += "--enable-tools \
	"

# We want to use bluez5 when doing handover
RRECOMMENDS_${PN} = "\
    ${@base_contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi','wpa-supplicant', '', d)} \
    "

# Activate neard by default
SYSTEMD_AUTO_ENABLE = "enable"
