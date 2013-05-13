require recipes-connectivity/neard/neard.inc

SRC_URI  = "git://git.kernel.org/pub/scm/network/nfc/neard.git;protocol=git \
           file://neard.service.in \
	"

S      = "${WORKDIR}/git"
SRCREV = "3365ffe98e856b87ce337c103153271e24dcef9f"
PV     = "0.x+git${SRCREV}"
PR     = "r1"


EXTRA_OECONF += "--enable-tools \
	"

# We want to use bluez5 when doing handover
RRECOMMENDS_${PN} = "\
    ${@base_contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi','wpa-supplicant', '', d)} \
    "

# Activate neard by default
SYSTEMD_AUTO_ENABLE = "enable"
