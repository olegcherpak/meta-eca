require recipes-connectivity/bluez5/bluez5.inc

SRCREV = "71e455c8b963e27d01250c012f91625ecfc11e90"
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
