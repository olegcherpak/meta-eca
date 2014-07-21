DESCRIPTION = "Simple web UI for Embedded Connectivity Appliance"
LICENSE  = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRCREV_default_pn-eca-web ?= "${AUTOREV}"
SRC_URI = "\
	git://github.com/jukkar/eca-web.git \
	file://eca-web.service \
	file://start-eca-web \
	file://config \
"

SRC_URI[md5sum] = "71469efd6798ea62c788db34d8da0a93"
SRC_URI[sha256sum] = "bc08d8345854c4ec6740bcef6d563fb3f8317f9a6a4b47ba327454a3b75f4afc"

S = "${WORKDIR}/git"
PR = "r0"

PREFIX ?= "/opt/eca-web"

EXTRA_OECONF += "\
    --prefix=${PREFIX} \
    --sysconfdir=/etc \
"

inherit autotools
inherit systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "eca-web.service"

FILES_${PN} = " \
	    eca-web.service \
	    start-eca-web \
	    /opt/eca-web/* \
	    ${sysconfdir}/${PN} \
	    ${systemd_unitdir}/system/${PN}.service \
"

RDEPENDS_${PN} = "webpy python-shell python-io"

do_install_append() {
	install -d ${D}${systemd_unitdir}/system
	install -m 0755 ${WORKDIR}/eca-web.service ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}/${PN}
	install -m 0644 ${WORKDIR}/config ${D}${sysconfdir}/${PN}
	install -m 0755 ${WORKDIR}/start-eca-web ${D}${PREFIX}
}

