require recipes-connectivity/bluez5/bluez5.inc

SRCREV = "e94fc26ea2e57b139ed2dbe0f5cd2e86d6b91b1f"
SRC_URI = "\
	git://git.kernel.org/pub/scm/bluetooth/bluez.git \
	file://bluetooth.conf \
	file://fix-udev-paths.patch \
"

S = "${WORKDIR}/git"
PV = "5.x+git${SRCREV}"

EXTRA_OECONF += "\
    --disable-obex \
    --enable-client \
"

do_install_append() {
	# gatttool is useful to have so add it to the bluez5 package
	install -m 0755 ${S}/attrib/gatttool ${D}/${bindir}
}
