DESCRIPTION = "Simple web UI for Embedded Communication Appliance"
LICENSE  = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"
PR = "r0"

SRC_URI = "\
	file://eca-web-0.1.tar.gz \
	file://eca-web.service \
	file://config \
"

S = "${WORKDIR}/eca-web-0.1"
PR = "r1"

PREFIX ?= "/opt/eca-web"

EXTRA_OECONF += "\
    --prefix=${PREFIX} \
    --sysconfdir=/etc \
"

inherit autotools
inherit systemd

SYSTEMD_PACKAGES = "${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd = "eca-web.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES_${PN} = " \
	    eca-web.service \
	    /opt/eca-web/* \
	    ${sysconfdir}/${PN} \
	    ${systemd_unitdir}/system/${PN}.service \
"

RDEPENDS_${PN} = "webpy"

do_install_append() {
	install -d ${D}${systemd_unitdir}/system
	install -m 0755 ${WORKDIR}/eca-web.service ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}/${PN}
	install -m 0644 ${WORKDIR}/config ${D}${sysconfdir}/${PN}
}

PACKAGES += " ${PN}-systemd"
