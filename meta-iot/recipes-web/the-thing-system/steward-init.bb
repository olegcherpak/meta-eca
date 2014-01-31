SUMMARY = "Initialization service for TheThingSystem steward"
DESCRIPTION = "Initializes TheThingSystem steward services."

LICENSE  = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.steward-init;md5=2fe93140f8c4e56b56fbcd64730767a4"

SRC_URI = "\
	file://steward-init-settings.sh \
	file://steward-init.service.in \
	file://LICENSE.steward-init \
"

S = "${WORKDIR}"
PR = "r2"

THE_THING_SYSTEM ?= "/opt/TheThingSystem"

inherit systemd
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "steward-init.service"

FILES_${PN} = "\
	${THE_THING_SYSTEM}/steward/* \
	${sysconfdir}/TheThingSystem/* \
"

do_install() {
	install -d ${D}${THE_THING_SYSTEM}/steward
	install -d ${D}${systemd_unitdir}/system
	install -m 0755 ${WORKDIR}/steward-init-settings.sh ${D}${THE_THING_SYSTEM}/steward

        sed 's,@the_thing_system_dir@,${THE_THING_SYSTEM},g' < ${WORKDIR}/steward-init.service.in \
           > ${D}${systemd_unitdir}/system/steward-init.service

	install -d ${D}/etc/TheThingSystem
	echo "# Configuration options for TheThingSystem/steward" > ${D}/etc/TheThingSystem/config
	echo "# Generated `date`" >> ${D}/etc/TheThingSystem/config
	echo "THE_THING_SYSTEM=${THE_THING_SYSTEM}" >> ${D}/etc/TheThingSystem/config
	echo "NODE_PATH=${THE_THING_SYSTEM}/steward" >> ${D}/etc/TheThingSystem/config
}
