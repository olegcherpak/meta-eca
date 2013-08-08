require recipes-connectivity/bluez5/bluez5.inc

SRCREV = "c4db4db6bfd5173e9c7e780089e3cecc93827f52"
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
