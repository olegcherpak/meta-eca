DESCRIPTION = "Node.js is a server-side JavaScript environment for TheThingSystem"
LICENSE  = "MIT & BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4a31e6c424761191227143b86f58a1ef"

S	= "${WORKDIR}/git"
SRCREV	= "cbff8f091c22fb1df6b238c7a1b9145db950fa65"
PV	= "0.10.22"

SRC_URI = "\
	git://github.com/joyent/node.git;branch=v0.10.22-release \
"

THE_THING_SYSTEM ?= "/opt/TheThingSystem"

DEPENDS = "openssl ninja-native"

# v8 errors out if you have set CCACHE
CCACHE = ""

ARCHFLAGS_arm = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard',\
	       '--with-arm-float-abi=hard', '--with-arm-float-abi=softfp', d)}"
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
