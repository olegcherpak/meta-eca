require recipes-connectivity/bluez5/bluez5.inc

SRCREV = "7a17b557ab5770a64c8e5daa8f90be708819e931"
SRC_URI = "\
	git://git.kernel.org/pub/scm/bluetooth/bluez.git \
	file://bluetooth.conf \
	file://fix-udev-paths.patch \
"

S = "${WORKDIR}/git"
PV = "5.x+git${SRCREV}"

EXTRA_OECONF += "\
    --disable-obex \
    --enable-pand \
    --enable-dund \
    --enable-client \
"
