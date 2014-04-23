require recipes-connectivity/neard/neard.inc

SRC_URI  = "git://git.kernel.org/pub/scm/network/nfc/neard.git;protocol=git \
           file://neard.service.in \
	"

S      = "${WORKDIR}/git"
SRCREV = "647f2705bebf051ea85ab74399461203c8ba900e"
PV     = "0.14+git${SRCPV}"
PR     = "r2"


EXTRA_OECONF += "--enable-tools \
	"

# We want to use bluez5 when doing handover
RRECOMMENDS_${PN} = "\
    ${@base_contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi','wpa-supplicant', '', d)} \
    "
