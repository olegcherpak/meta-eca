require recipes-connectivity/neard/neard.inc

SRC_URI  = "\
	 git://git.kernel.org/pub/scm/network/nfc/neard.git;protocol=git \
	 file://neard.service.in \
	 file://Makefile.am-fix-parallel-issue.patch \
"

LIC_FILES_CHKSUM = "\
	 file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e \
	 file://src/near.h;beginline=1;endline=20;md5=358e4deefef251a4761e1ffacc965d13 \
"

S      = "${WORKDIR}/git"
SRCREV = "29fb0096f1b7b1b7437f2fccdd19c8a64ecb44e6"
PV     = "0.15+git${SRCPV}"
PR     = "r3"


EXTRA_OECONF += "--enable-tools \
	"

# We want to use bluez5 when doing handover
RRECOMMENDS_${PN} = "\
    ${@base_contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
    ${@base_contains('DISTRO_FEATURES', 'wifi','wpa-supplicant', '', d)} \
    "

# Neard needs $systemd_unitdir in place during configure...
DEPENDS = "systemd"
