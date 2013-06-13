def get_depends_qemu(bb, d, dep):
    val = (bb.data.getVar("MACHINEOVERRIDES", d) or "")
    if val.find("qemuall"):
        return ""
    else:
        return dep

IWLWIFI := "\
    ${@get_depends_qemu(bb, d, 'linux-firmware-iwlwifi-licence')} \
    ${@get_depends_qemu(bb, d, 'linux-firmware-iwlwifi-6000g2a-5')} \
    ${@get_depends_qemu(bb, d, 'linux-firmware-iwlwifi-6000g2b-6')} \
"

# Add iwlwifi firmware for Intel devices but not for qemu images because
# user probably does not have Intel layer defined there
RDEPENDS_packagegroup-eca_append_x86 += "\
    ${IWLWIFI} \
"

# network configuration for connman if running qemu
RDEPENDS_packagegroup-eca_append_qemuall += "\
    connman-conf \
"
