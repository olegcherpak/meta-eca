DESCRIPTION = "The Thing System steward"
LICENSE  = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=2fe93140f8c4e56b56fbcd64730767a4"

SRC_URI = "\
	git://github.com/TheThingSystem/steward.git \
	file://steward.service.in \
	file://start-steward \
	file://start-steward.debug \
	file://server.js.in \
	file://package.json \
"

#SRCREV_default_pn-steward = "${AUTOREV}"
SRCREV = "311f48529d07ceefafd29ede9da5beeb54312f34"
S = "${WORKDIR}/git"
PR = "r2"
PV = "1.5+git${SRCREV}"

DEPENDS = "tts-nodejs-native"
DEPENDS_${PN} = "\
	libdns-sd-dev \
	libpcap-dev \
	libusb1 \
	libbluetooth-dev \
	${@base_contains('DISTRO_FEATURES', 'systemd', 'systemd-dev', 'libudev-dev', d)} \
"

THE_THING_SYSTEM ?= "/opt/TheThingSystem"

inherit systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "steward.service"

FILES_${PN} = "\
	${THE_THING_SYSTEM}/* \
	${libdir} \
"

FILES_${PN}-dbg += "\
	${THE_THING_SYSTEM}/steward/*/*/*/.debug \
	${THE_THING_SYSTEM}/steward/*/*/*/*/.debug \
	${THE_THING_SYSTEM}/steward/*/*/*/*/*/.debug \
	${THE_THING_SYSTEM}/steward/*/*/*/*/*/*/.debug \
	${THE_THING_SYSTEM}/steward/*/*/*/*/*/*/*/.debug \
	${THE_THING_SYSTEM}/steward/*/*/*/*/*/*/*/*/.debug \
	${THE_THING_SYSTEM}/steward/*/*/*/*/*/*/*/*/*/.debug \
"

RDEPENDS_${PN} = "openssl tts-nodejs steward-init ruby"

def get_arch(bb, d):
    val = (bb.data.getVar("MACHINEOVERRIDES", d) or "")
    if val.find("genericx86") > 0:
        return "--arch=i686"
    elif val.find("x86") > 0:
        return "--arch=i686"
    elif val.find("arm") > 0:
        return "--arch=arm"
    else:
        return ""

# Always compile 32-bit in npm because many modules that npm
# compiles do not support 64 bit in x86.
TTS_ARCH := "${@get_arch(bb, d)}"

do_install_append() {
	install -d ${D}${THE_THING_SYSTEM}/steward
	install -d ${D}${systemd_unitdir}/system
	install -m 0755 ${WORKDIR}/start-steward ${D}${THE_THING_SYSTEM}/steward
	install -m 0755 ${WORKDIR}/start-steward.debug ${D}${THE_THING_SYSTEM}/steward

        sed 's,@the_thing_system_dir@,${THE_THING_SYSTEM},g' \
           < ${WORKDIR}/steward.service.in \
           > ${D}${systemd_unitdir}/system/steward.service
        sed 's,@the_thing_system_dir@,${THE_THING_SYSTEM},g' \
           < ${WORKDIR}/server.js.in \
           > ${D}${THE_THING_SYSTEM}/steward/server.js

	cp -pR ${S}/steward/* ${D}${THE_THING_SYSTEM}/steward/
	install -m 0644 ${WORKDIR}/package.json ${D}${THE_THING_SYSTEM}/steward
	rm -rf ${D}${THE_THING_SYSTEM}/steward/sandbox/js-beautify-master/tests
	rm ${D}${THE_THING_SYSTEM}/steward/run.sh
	find ${D}${THE_THING_SYSTEM} -name .gitignore -exec rm '{}' \;

	# Setup the node.js environment
	cd ${D}${THE_THING_SYSTEM}/steward

	# There seems to be some issues (npm hanging) if you try to run
	# install when behind the proxy. So we try to setup proxies for npm
	# See README.iot file for details.
	if [ -x ~/npm-setup-proxies ]; then ~/npm-setup-proxies; fi

	# Cleaning cache should help to some weird compilation errors
	npm cache clean

	# Do a fresh start
	rm -rf node_modules

	# Telling npm to use known registrars will prevent this error
	#   | npm ERR! Error: SSL Error: SELF_SIGNED_CERT_IN_CHAIN
	# when running npm
	npm config set ca ""

	# Some of the node.js packages put -pthreads into ld params and ld
	# does not understand it. So install packages using gcc as a linker.
	LD=${TARGET_PREFIX}gcc npm install --production -l ${TTS_ARCH}

	# No need for static libraries
	find ${D}${THE_THING_SYSTEM} -name '*.a' -exec rm '{}' \;

	# Remove not used files
	rm -rf ${D}${THE_THING_SYSTEM}/steward/node_modules/xml2js/node_modules/sax/examples
	rm -rf ${D}${THE_THING_SYSTEM}/steward/node_modules/openzwave/deps/open-zwave/debian

	# Remove some garbage files that prevent image creation
	rm -f ${D}${THE_THING_SYSTEM}/steward/node_modules/pcap/*%*

	# Blinkstick does not load in this version so just remove it
	rm -rf ${D}${THE_THING_SYSTEM}/steward/node_modules/blinkstick
	rm -rf ${D}${THE_THING_SYSTEM}/steward/devices/devices-lighting/lighting-blinkstick-led.js

}
