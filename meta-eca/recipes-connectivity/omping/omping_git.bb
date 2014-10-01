
SRC_URI = "git://git.fedorahosted.org/git/omping.git"

SRCREV = "${AUTOREV}"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "\
	file://COPYING;md5=169912ab7a9a7c467dfb20c86352e879 \
	file://omping.c;beginline=1;endline=15;md5=50bd0deeca8967a0781499b357148c7b \
"

PR = "r0"

S = "${WORKDIR}/git"
PV = "0.0.4+git${SRCPV}"

do_install() {
     oe_runmake PREFIX=/usr DESTDIR=${D} install
}

PACKAGES =+ "${PN}-manual"
FILES_${PN} += "${bindir}"
FILES_${PN}-manual += "${mandir}"
