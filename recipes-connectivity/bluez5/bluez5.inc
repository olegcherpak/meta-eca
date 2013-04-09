SUMMARY = "Linux Bluetooth Stack Userland V5"
DESCRIPTION = "Linux Bluetooth stack V5 userland components.  These include a system configurations, daemons, tools and system libraries."
HOMEPAGE = "http://www.bluez.org"
SECTION = "libs"
LICENSE = "GPLv2+ & LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e \
                    file://COPYING.LIB;md5=fb504b67c50331fc78734fed90fb0e09 \
                    file://src/main.c;beginline=1;endline=24;md5=9bc54b93cd7e17bf03f52513f39f926e"
DEPENDS = "udev libusb dbus-glib glib-2.0 libcheck libical"

PACKAGECONFIG ??= "\
    ${@base_contains('DISTRO_FEATURES', 'alsa', 'alsa', '', d)}\
    ${@base_contains('DISTRO_FEATURES', 'pie', 'pie', '', d)}\
"
PACKAGECONFIG[alsa] = "--enable-alsa,--disable-alsa,alsa-lib"
PACKAGECONFIG[pie] = "--enable-pie,--disable-pie,"

ASNEEDED = ""

INC_PR = "r1"

inherit autotools pkgconfig systemd

EXTRA_OECONF = "\
  --disable-gstreamer \
  --enable-usb \
  --enable-tools \
  --enable-bccmd \
  --enable-hid2hci \
  --enable-dfutool \
  --disable-hidd \
  --enable-pand \
  --enable-dund \
  --disable-cups \
  --enable-test \
  --enable-datafiles \
  --with-udevdir=${base_libdir}/udev \
  --with-udevrulesdir=${nonarch_base_libdir}/udev/rules.d \
  ${@base_contains('DISTRO_FEATURES', 'systemd', '--with-systemdunitdir=${systemd_unitdir}/system/', '--disable-systemd', d)} \
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
