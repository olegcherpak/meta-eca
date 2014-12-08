require recipes-connectivity/connman/connman.inc

# We use a known good version instead of some buggy point version.
SRCREV = "3240a051826bb665fbd5cc0d9eb7c2bdde3ad77a"

SRC_URI = "\
	git://git.kernel.org/pub/scm/network/connman/connman.git \
	file://add_xuser_dbus_permission.patch \
	file://connman.service \
"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e \
                    file://src/main.c;beginline=1;endline=20;md5=486a279a6ab0c8d152bcda3a5b5edc36 \
"

# Enable debugging the easy way in systemd based distro.
# See 0001-doc-Debugging-in-host-that-uses-systemd.patch for usage.
SRC_URI += "\
    file://0003-systemd-Use-environment-file-for-connman-vpnd-debug-.patch \
"

S = "${WORKDIR}/git"
PR = "${INC_PR}.3"
PV = "1.26+git${SRCPV}"


# Override some options from poky connman recipe
EXTRA_OECONF += "\
    --enable-loopback=builtin \
    --enable-ethernet=builtin \
    --enable-test \
    --enable-client \
    --enable-tools \
    --disable-polkit \
    --enable-pacrunner \
    --enable-wispr \
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
	install -m 0755 ${WORKDIR}/connman.service ${D}${nonarch_base_libdir}/systemd/system
}
