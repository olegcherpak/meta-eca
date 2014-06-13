require recipes-connectivity/bluez5/bluez5.inc

SRCREV = "95f338a7fa8807c196ffa37c82b201ba4b987c3d"
SRC_URI = "\
	git://git.kernel.org/pub/scm/bluetooth/bluez.git \
	file://bluetooth.conf \
	file://fix-udev-paths.patch \
"

S = "${WORKDIR}/git"
PV = "5.19+git${SRCPV}"

EXTRA_OECONF += "\
    --disable-obex \
    --enable-client \
"

do_install_append() {
	# gatttool is useful to have so add it to the bluez5 package
	install -m 0755 ${S}/attrib/gatttool ${D}/${bindir}
}
