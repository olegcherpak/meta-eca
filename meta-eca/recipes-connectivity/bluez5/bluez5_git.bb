require recipes-connectivity/bluez5/bluez5.inc

SRCREV = "83c7a21fe4e183095ce6020d6925a5c36dc99142"
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
