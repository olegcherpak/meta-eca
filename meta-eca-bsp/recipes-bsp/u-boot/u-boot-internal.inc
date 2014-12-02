FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PV = "v2014.04-2"
S = "${WORKDIR}/git"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.denx.de/u-boot.git;branch=master"
SRC_URI_edison += "file://upstream_to_edison.patch"
SRC_URI_edison += "file://0001-x86-interrupts-Avoid-overflowing-the-irq_handlers-ar.patch"
SRC_URI_edison += "file://${MACHINE}.env"
SRC_URI_edison += "file://target_env/*.env"

SRCREV = "dda0dbfc69f3d560c87f5be85f127ed862ea6721"
