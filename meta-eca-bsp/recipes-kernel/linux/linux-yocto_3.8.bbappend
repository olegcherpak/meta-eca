# find defconfig path
FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

# Kernel configs
SRC_URI_append_beagleboard = " \
        file://beagle_qemu.cfg \
        "

SRC_URI_append_vexpressa9 = " \
        file://vexpress_a9.cfg  \
        "

KBRANCH_vexpressa9 = "standard/beagleboard"
SRCREV_machine_vexpressa9 ?= "AUTOINC"
COMPATIBLE_MACHINE_vexpressa9 = "vexpressa9"
