SUMMARY  = "Linux NFC daemon"
DESCRIPTION = "A daemon for the Linux Near Field Communication stack"
HOMEPAGE = "http://01.org/linux-nfc"

LICENSE  = "GPLv2"

DEPENDS  = "dbus glib-2.0 libnl"

inherit autotools pkgconfig

do_install() {
	oe_runmake DESTDIR=${D} libexecdir=${libexecdir} install
}

# This would copy neard test scripts
do_install_append() {
	#test files
	install -d ${D}${libdir}/neard
	install -m 0755 ${S}/test/* ${D}${libdir}/${BPN}/
	install -m 0755 ${S}/tools/nfctool/nfctool ${D}${libdir}/${BPN}/
}

RDEPENDS_${PN} = "dbus python python-dbus python-pygobject"

# Bluez & Wifi are not mandatory except for handover	"
RRECOMMENDS_${PN} = "\
	${@base_contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
	${@base_contains('DISTRO_FEATURES', 'wifi','wpa-supplicant', '', d)} \
	"

#Additional
PACKAGES =+ "${PN}-tests"

FILES_${PN}-tests = "${libdir}/${BPN}/*-test"
FILES_${PN}-dbg += "${libdir}/${BPN}/*/.debug"

RDEPENDS_${PN}-tests = "python python-dbus python-pygobject"

# This is valid for 0.10+
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e \
 file://src/near.h;beginline=1;endline=20;md5=358e4deefef251a4761e1ffacc965d13 \
 "
S	= "${WORKDIR}/git"
SRCREV = "3365ffe98e856b87ce337c103153271e24dcef9f"
PV	= "0.x+git${SRCPV}"
PR	= "r1"

SRC_URI  = "git://git.kernel.org/pub/scm/network/nfc/neard.git;protocol=git \
	"

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

