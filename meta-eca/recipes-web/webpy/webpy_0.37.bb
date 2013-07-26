DESCRIPTION = "web.py: makes web apps"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=98bdd61465b0278f5e6b8e81a0c981f2"
PR = "r0"

# 0.37 tag
SRCREV = "7a08b0636a15d592e0a04e7adabe48abdb262b0a"
SRC_URI = "\
	git://github.com/webpy/webpy.git \
"
S = "${WORKDIR}/git"

inherit distutils

RDEPENDS_${PN} = "\
    python-netserver \
    python-compiler \
"
