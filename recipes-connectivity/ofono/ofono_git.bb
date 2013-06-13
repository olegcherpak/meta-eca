require recipes-connectivity/ofono/ofono.inc

S	= "${WORKDIR}/git"
SRCREV	= "fc7de0f6e18e6b9cf874207cc78d9993d122f3ff"
PV	= "1.x+git${SRCREV}"

SRC_URI = "\
	git://git.kernel.org/pub/scm/network/ofono/ofono.git;protocol=git \
	file://ofono \
"

EXTRA_OECONF += "\
    --enable-test \
    ${@base_contains('DISTRO_FEATURES', 'bluetooth','--enable-bluetooth', '--disable-bluetooth', d)} \
"

# We want to use bluez5
DEPENDS := "${@oe_filter_out('bluez4', '${DEPENDS}', d)}"
DEPENDS += "${@base_contains('DISTRO_FEATURES', 'bluetooth','bluez5', '', d)}"


# Use systemd and enable ofono by default
inherit systemd

EXTRA_OECONF += "--with-systemdunitdir=${systemd_unitdir}/system/"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "ofono.service"

do_install_append() {
	# Remove init scripts
	rm -rf ${D}${sysconfdir}/init.d
}
