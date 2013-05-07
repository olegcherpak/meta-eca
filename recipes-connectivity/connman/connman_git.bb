require recipes-connectivity/connman/connman.inc

SRCREV = "5ad9b3c94a3414d216b5349af4d67f2cfb30b59e"
SRC_URI = "\
	git://git.kernel.org/pub/scm/network/connman/connman.git \
	file://add_xuser_dbus_permission.patch \
	file://inet-fix-ip-cleanup-functions.patch \
"

# Add git commit id to connman version. Needed so that we know
# what connman version we are running. The patch also requires
# that we enable maintainer mode
EXTRA_OECONF += " --enable-maintainer-mode "
SRC_URI += "\
    file://0001-build-Script-to-generate-current-git-HEAD-commit-id.patch \
    file://0002-build-Use-detailed-version-information-when-printing.patch \
"

# Enable debugging the easy way in systemd based distro.
# See 0001-doc-Debugging-in-host-that-uses-systemd.patch for usage.
SRC_URI += "\
    file://0002-systemd-Use-environment-file-for-connmand-debug-opti.patch \
    file://0003-systemd-Use-environment-file-for-connman-vpnd-debug-.patch \
"

# Check if we already know the service credentials before activating
# agent. Typically this helps in a case where we have multiple wifi
# cards and know the credentials for one service. As the credentials
# are stored separately for each service, we can try to use those
# known passphrases first.
SRC_URI += "\
    file://0001-service-We-might-know-the-passphrase-for-SSID-alread.patch \
    file://0002-service-Debug-print-the-error-we-are-sending.patch \
    file://0003-service-Set-error-after-changing-state.patch \
"

S = "${WORKDIR}/git"
PR = "${INC_PR}.0"
PV = "1.x+git${SRCREV}"


# In ARM tweak the CFLAGS and remove cast-align because the GCC 4.7.2
# gives "cast increases required alignment of target type" warning
# which aborts connman compilation
CFLAGS_prepend_arm = " -Wno-error=cast-align "


# Override some options from poky connman recipe
EXTRA_OECONF += "\
    --enable-loopback=builtin \
    --enable-ethernet=builtin \
    --enable-test \
    --enable-client \
    --enable-tools \
    --disable-fake \
    --disable-polkit \
    --disable-threads \
"


# Make sure we will use bluez5 instead of older bluez4
PACKAGECONFIG[bluetooth] = "--enable-bluetooth, --disable-bluetooth, bluez5"
RDEPENDS_${PN} = "\
	dbus \
	${@base_contains('PACKAGECONFIG', 'bluetooth', 'bluez5', '', d)} \
	${@base_contains('PACKAGECONFIG', 'wifi','wpa-supplicant', '', d)} \
	${@base_contains('PACKAGECONFIG', '3g','ofono', '', d)} \
	"


do_install_append() {
	install -d ${D}${sysconfdir}/connman
	install -m 0644 ${S}/src/main.conf ${D}${sysconfdir}/connman/main.conf.example
}
