require recipes-connectivity/neard/neard.inc

SRC_URI  = "\
	 git://git.kernel.org/pub/scm/network/nfc/neard.git;protocol=git \
	 file://neard.service.in \
"

LIC_FILES_CHKSUM = "\
	 file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e \
	 file://src/near.h;beginline=1;endline=20;md5=fa0b3e277aa4c27c9d45fd5828f7494e \
"

S      = "${WORKDIR}/git"
SRCREV = "00c9abd9411e9d262cf5de90734522ab6bc0bca5"
PV     = "0.14+git${SRCPV}"
PR     = "r5"


EXTRA_OECONF += "--enable-tools \
	"

# We want to use bluez5 when doing handover
RRECOMMENDS_${PN} = "\
    ${@base_contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi','wpa-supplicant', '', d)} \
    "
