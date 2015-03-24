# unstrung is an IETF roll - RPL (ripple) implementation for Linux
# See RFC6550 (IPv6 Routing Protocol for Low-Power and Lossy Networks)
# for details.

SRCREV = "${AUTOREV}"
SRC_URI = "\
	git://github.com/mcr/unstrung.git \
	file://0001-make-Double-DESTDIR-in-manual-paths.patch \
"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "\
	file://programs/sunshine/main.cpp;beginline=4;endline=12;md5=1550270a3c8e1f36d3c7c8426aee46e2 \
"

PR = "r0"
S = "${WORKDIR}/git"
PV = "1.0+git${SRCPV}"

DEPENDS = "libpcap libusb1"

PACKAGES =+ "${PN}-manual ${PN}-scripts"
FILES_${PN}-manual += "${mandir}"
FILES_${PN}-scripts += "${datadir}/${PN}"

do_install_append() {
	oe_runmake DESTDIR=${D} install

	# Copy also simple test scripts
	mkdir -p ${D}/${datadir}/${PN}
	cp -p ${S}/programs/sunshine/n?.sh ${D}/${datadir}/${PN}
}
