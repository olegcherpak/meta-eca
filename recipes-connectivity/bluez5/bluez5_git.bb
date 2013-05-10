require bluez5.inc

SRCREV = "b6e28da5ff5d08196a530733fddb0650ded3309b"
SRC_URI = "\
	git://git.kernel.org/pub/scm/bluetooth/bluez.git \
	file://bluetooth.conf \
	file://fix-udev-paths.patch \
"

# Add git commit id to bluez version. Needed so that we know
# what bluez version we are running (important if using the
# bleeding edge version)
SRC_URI += "\
    file://0001-build-Script-to-generate-current-git-HEAD-commit-id.patch \
    file://0002-build-Use-detailed-version-information-when-printing.patch \
"

S = "${WORKDIR}/git"
PR = "${INC_PR}.0"
PV = "5.x+git${SRCREV}"

RCONFLICTS_${PN} = "bluez4"
RREPLACES_${PN} = "bluez4"

do_install_append() {
	install -d ${D}${sysconfdir}/bluetooth/
	install -m 0644 ${S}/profiles/audio/audio.conf ${D}/${sysconfdir}/bluetooth/
	install -m 0644 ${S}/profiles/network/network.conf ${D}/${sysconfdir}/bluetooth/
	install -m 0644 ${S}/profiles/input/input.conf ${D}/${sysconfdir}/bluetooth/
	# at_console doesn't really work with the current state of OE, so punch some more holes so people can actually use BT
	install -m 0644 ${WORKDIR}/bluetooth.conf ${D}/${sysconfdir}/dbus-1/system.d/
}

ALLOW_EMPTY_libasound-module-bluez = "1"
PACKAGES =+ "libasound-module-bluez ${PN}-test"

FILES_libasound-module-bluez = "${libdir}/alsa-lib/lib*.so ${datadir}/alsa"
FILES_${PN} += "${libdir}/bluetooth/plugins ${libdir}/bluetooth/plugins/*.so ${base_libdir}/udev/ ${nonarch_base_libdir}/udev/ ${systemd_unitdir}/ ${datadir}/dbus-1"
FILES_${PN}-dev += "\
  ${libdir}/bluetooth/plugins/*.la \
  ${libdir}/alsa-lib/*.la \
"

FILES_${PN}-test = "${libdir}/bluez/test/*"

FILES_${PN}-dbg += "\
  ${libdir}/${PN}/bluetooth/.debug \
  ${libdir}/bluetooth/plugins/.debug \
  ${libdir}/*/.debug \
  ${base_libdir}/udev/.debug \
  "

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "bluetooth.service"

do_install_append() {
	# Remove init scripts
	case "${DISTRO_FEATURES}" in
		*systemd*) rm -rf ${D}${sysconfdir}/init.d/
	esac
}
