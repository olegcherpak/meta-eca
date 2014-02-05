SUMMARY = "Initialization service for Avahi daemon"
DESCRIPTION = "Initializes Avahi daemon service."

LICENSE  = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.avahi-daemon-init;md5=2fe93140f8c4e56b56fbcd64730767a4"

FILESEXTRAPATHS := "${THISDIR}/${PN}"
SRC_URI = "\
	file://avahi-daemon-init-settings.sh \
	file://avahi-daemon-init.service \
	file://LICENSE.avahi-daemon-init \
"

S = "${WORKDIR}"
PR = "r2"

inherit systemd
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = " avahi-daemon-init.service"

FILES_${PN} = "\
	avahi-daemon-init.service \
	${bindir}/avahi-daemon-init-settings.sh \
"

do_install() {
	install -d ${D}${bindir}
	install -d ${D}${systemd_unitdir}/system
	install -m 0755 ${WORKDIR}/avahi-daemon-init-settings.sh ${D}${bindir}/
	install -m 0755 ${WORKDIR}/avahi-daemon-init.service ${D}${systemd_unitdir}/system
}
