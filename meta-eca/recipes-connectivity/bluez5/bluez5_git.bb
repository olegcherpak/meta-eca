require recipes-connectivity/bluez5/bluez5.inc

SRCREV = "e84a0fff8adb39358cbd1e3c9bc27ea6f0b5d262"
SRC_URI = "\
	git://git.kernel.org/pub/scm/bluetooth/bluez.git \
	file://bluetooth.conf \
	file://fix-udev-paths.patch \
"

S = "${WORKDIR}/git"
PV = "5.25+git${SRCPV}"

EXTRA_OECONF += "\
    --disable-obex \
    --enable-client \
"

do_install_append() {
	# gatttool is useful to have so add it to the bluez5 package
	install -m 0755 ${S}/attrib/gatttool ${D}/${bindir}
}
