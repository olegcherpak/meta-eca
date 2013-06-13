# find defconfig path
FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

# Kernel configs
SRC_URI_append_beagleboard = " \
        file://beagle_qemu.cfg \
        "
