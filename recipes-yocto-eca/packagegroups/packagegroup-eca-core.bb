DESCRIPTION = "ECA 0.2 packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${ECA_COREBASE}/meta-eca/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"

hostname_pn-base-files = "eca"

inherit packagegroup

PACKAGES = "\
    packagegroup-eca-core \
    "

ALLOW_EMPTY_packagegroup-eca-core = "1"

RDEPENDS_packagegroup-eca-core = "\
    "
