DESCRIPTION = "Node.js is a server-side JavaScript environment for TheThingSystem"
LICENSE  = "MIT & BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c61ec54d119e218dbe45d9f69a421b5f"

S	= "${WORKDIR}/git"
SRCREV	= "597fa2921db63b57fc7367e156da226f8174271c"
PV	= "0.10.x+git${SRCREV}"

SRC_URI = "\
	git://github.com/TheThingSystem/node.git \
"

THE_THING_SYSTEM ?= "/opt/TheThingSystem"

DEPENDS = "openssl ninja-native"

# v8 errors out if you have set CCACHE
CCACHE = ""

ARCHFLAGS_arm = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--with-arm-float-abi=hard', '--with-arm-float-abi=softfp', d)}"
ARCHFLAGS ?= ""

do_configure() {
    export LD="${CXX}"
    ./configure --ninja --prefix=${prefix} --without-snapshot ${ARCHFLAGS}
}

do_compile() {
    export LD="${CXX}"
    make BUILDTYPE=Release
}

do_install() {
   oe_runmake install DESTDIR=${D}
}

FILES_${PN} = "\
	${libdir} \
	${bindir} \
"

RDEPENDS_${PN} = "curl python-shell python-datetime python-subprocess \
		 python-crypt python-textutils python-netclient \
		 python-misc python-multiprocessing \
"
RDEPENDS_${PN}_class-native = ""

BBCLASSEXTEND = "native"
