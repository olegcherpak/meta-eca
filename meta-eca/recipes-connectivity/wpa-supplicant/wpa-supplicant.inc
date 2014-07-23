SUMMARY = "Client for Wi-Fi Protected Access (WPA)"
HOMEPAGE = "http://hostap.epitest.fi/wpa_supplicant/"
BUGTRACKER = "http://hostap.epitest.fi/bugz/"
SECTION = "network"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=ab87f20cd7e8c0d0a6539b34d3791d0e \
                    file://README;beginline=1;endline=56;md5=a07250b28e857455336bb59fc31cb845 \
                    file://wpa_supplicant/wpa_supplicant.c;beginline=1;endline=12;md5=e8e021e30f3a6ab7c341b66b86626a5a"
DEPENDS = "gnutls dbus libnl openssl libgcrypt"

inherit systemd

SYSTEMD_SERVICE_${PN} = "wpa_supplicant.service wpa_supplicant-nl80211@.service wpa_supplicant-wired@.service"
SYSTEMD_AUTO_ENABLE = "disable"

FILES_${PN} += "${datadir}/dbus-1/system-services/*"

do_configure () {
	install -m 0755 ${WORKDIR}/defconfig-gnutls wpa_supplicant/.config
	echo "CFLAGS +=\"-I${STAGING_INCDIR}/libnl3\"" >> wpa_supplicant/.config
}

export EXTRA_CFLAGS = "${CFLAGS}"
export BINDIR = "${sbindir}"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS
	sed -e "s:CFLAGS\ =.*:& \$(EXTRA_CFLAGS):g" -i ${S}/src/lib.rules
	oe_runmake -C wpa_supplicant
}

do_install () {
	install -d ${D}${sbindir}
	install -m 755 wpa_supplicant/wpa_supplicant ${D}${sbindir}

	install -d ${D}${sysconfdir}
	install -d ${D}/${sysconfdir}/dbus-1/system.d
	install -m 644 ${S}/wpa_supplicant/dbus/dbus-wpa_supplicant.conf ${D}/${sysconfdir}/dbus-1/system.d
	install -d ${D}/${datadir}/dbus-1/system-services
	install -m 644 ${S}/wpa_supplicant/dbus/*.service ${D}/${datadir}/dbus-1/system-services

	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		install -d ${D}/${systemd_unitdir}/system
		install -m 644 ${S}/wpa_supplicant/systemd/*.service ${D}/${systemd_unitdir}/system
	fi
}

pkg_postinst_wpa-supplicant () {
	# If we're offline, we don't need to do this.
	if [ "x$D" = "x" ]; then
		killall -q -HUP dbus-daemon || true
	fi

}
