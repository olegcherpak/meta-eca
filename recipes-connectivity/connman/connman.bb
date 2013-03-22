require connman.inc

SRCREV = "${AUTOREV}"
SRC_URI = "\
	git://git.kernel.org/pub/scm/network/connman/connman.git \
	file://add_xuser_dbus_permission.patch \
	file://add-in.h-for-ipv6.patch \
	file://inet-fix-ip-cleanup-functions.patch \
"


S = "${WORKDIR}/git"
PR = "${INC_PR}.0"

PV = "1.x+gitr${SRCREV}"
