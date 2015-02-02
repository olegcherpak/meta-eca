SUMMARY = "A mode switching tool for controlling 'flip flop' (multiple device) USB gear"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = "libusb1"

SRC_URI = "http://www.draisberghof.de/usb_modeswitch/${BP}.tar.bz2"
SRC_URI[md5sum] = "46cd7fa937655ab2df616c1d5dfb121e"
SRC_URI[sha256sum] = "37d926a0b6b6d490173c57e260a69aca49feb774875fc8f2001c1d0f5e8011cb"

EXTRA_OEMAKE = "TCL=${bindir}/tclsh"

FILES_${PN} = "${bindir} ${sysconfdir} ${base_libdir}/udev/usb_modeswitch ${sbindir} ${localstatedir}/lib/usb_modeswitch"
RDEPENDS_${PN} = "tcl"
RRECOMMENDS_${PN} = "usb-modeswitch-data"

do_install() {
	oe_runmake DESTDIR=${D} install
}
