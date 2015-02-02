SUMMARY = "A simple ncurses interface for ConnMan"
DESCRIPTION = "A simple ncurses interface for ConnMan"
HOMEPAGE = "https://github.com/eurogiciel-oss/connman-json-client"

SECTION = "console/network"

DEPENDS = "dbus ncurses connman json-c pkgconfig"
RDEPENDS_${PN} = "dbus ncurses connman json-c"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "git://github.com/eurogiciel-oss/connman-json-client.git;rev=524cb1507523feea3db2f1e66ce1930be17c2115"

inherit autotools

EXTRA_AUTORECONF += " -i"
EXTRA_OECONF += " --disable-optimization --enable-debug"

S = "${WORKDIR}/git"

do_install () {
    mkdir -p ${D}${bindir}
    install -Dm755 connman_ncurses ${D}${bindir}/${PN}
}

FILES_${PN} = "${bindir}/${PN}"
