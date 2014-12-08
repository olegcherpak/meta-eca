require recipes-connectivity/neard/neard.inc

SRC_URI  = "\
	 git://git.kernel.org/pub/scm/network/nfc/neard.git;protocol=git \
	 file://neard.service.in \
"

LIC_FILES_CHKSUM = "\
	 file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e \
	 file://src/near.h;beginline=1;endline=20;md5=358e4deefef251a4761e1ffacc965d13 \
"

S      = "${WORKDIR}/git"
SRCREV = "80113dd4d96bcb66aeb3f35c075f65d85ab742be"
PV     = "0.15+git${SRCPV}"
PR     = "r1"


EXTRA_OECONF += "--enable-tools \
	"

# We want to use bluez5 when doing handover
RRECOMMENDS_${PN} = "\
    ${@base_contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi','wpa-supplicant', '', d)} \
    "
