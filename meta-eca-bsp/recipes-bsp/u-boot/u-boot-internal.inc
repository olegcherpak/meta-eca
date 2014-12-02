FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PV_edison = "v2014.04-2"
S_edison = "${WORKDIR}/git"
PACKAGE_ARCH_edison = "${MACHINE_ARCH}"

SRC_URI_edison = "git://git.denx.de/u-boot.git;branch=master"
SRC_URI_edison += "file://upstream_to_edison.patch"
SRC_URI_edison += "file://0001-x86-interrupts-Avoid-overflowing-the-irq_handlers-ar.patch"
SRC_URI_edison += "file://${MACHINE}.env"
SRC_URI_edison += "file://target_env/*.env"
SRCREV_edison = "dda0dbfc69f3d560c87f5be85f127ed862ea6721"
