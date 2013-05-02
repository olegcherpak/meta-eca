require recipes-connectivity/ofono/ofono.inc

S	= "${WORKDIR}/git"
SRCREV	= "fc7de0f6e18e6b9cf874207cc78d9993d122f3ff"
PV	= "1.x+git${SRCREV}"

SRC_URI = "\
	git://git.kernel.org/pub/scm/network/ofono/ofono.git;protocol=git \
	file://ofono \
"

# Add git commit id to ofono version. Needed so that we know
# what ofono version we are running. The patch also requires
# that we enable maintainer mode
EXTRA_OECONF += " --enable-maintainer-mode "
SRC_URI += "\
    file://0001-build-Script-to-generate-current-git-HEAD-commit-id.patch \
    file://0002-build-Use-detailed-version-information-when-printing.patch \
"

EXTRA_OECONF += "\
    --enable-test \
    ${@base_contains('DISTRO_FEATURES', 'bluetooth','--enable-bluetooth', '--disable-bluetooth', d)} \
"

# In ARM tweak the CFLAGS and remove cast-align errors because the GCC 4.7.2
# gives "cast increases required alignment of target type" warning
# which aborts ofono compilation
CFLAGS_prepend_arm = " -Wno-error=cast-align "


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
