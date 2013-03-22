require connman.inc

SRCREV = "${AUTOREV}"
SRC_URI = "\
	git://git.kernel.org/pub/scm/network/connman/connman.git \
	file://add_xuser_dbus_permission.patch \
	file://add-in.h-for-ipv6.patch \
	file://inet-fix-ip-cleanup-functions.patch \
"

# Start tethering automatically when device boots if it was
# activated when device shutdown.
SRC_URI += " file://0001-technology-Start-tethering-automatically.patch "

# Add git commit id to connman version. Needed so that we know
# what connman version we are running.
SRC_URI += "\
    file://0001-build-Script-to-generate-current-git-HEAD-commit-id.patch \
    file://0002-build-Use-detailed-version-information-when-printing.patch \
"

S = "${WORKDIR}/git"
PR = "${INC_PR}.0"

PV = "1.x+gitr${SRCREV}"
