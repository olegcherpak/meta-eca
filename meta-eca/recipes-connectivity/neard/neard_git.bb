require recipes-connectivity/neard/neard.inc

SRC_URI  = "git://git.kernel.org/pub/scm/network/nfc/neard.git;protocol=git \
           file://neard.service.in \
	"

S      = "${WORKDIR}/git"
SRCREV = "2b7240569750ab5a2282c80d4a34ea001ffc17dc"
PV     = "0.14+git${SRCPV}"
PR     = "r3"


EXTRA_OECONF += "--enable-tools \
	"

# We want to use bluez5 when doing handover
RRECOMMENDS_${PN} = "\
    ${@base_contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi','wpa-supplicant', '', d)} \
    "
