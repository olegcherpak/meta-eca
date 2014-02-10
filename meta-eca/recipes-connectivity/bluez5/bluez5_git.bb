require recipes-connectivity/bluez5/bluez5.inc

SRCREV = "4a9ce2ed68f0b8e2904ade4e5e376c0ad8570556"
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
