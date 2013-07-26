FILESEXTRAPATHS := "${THISDIR}/${PN}"

# Default root password is "root"
SRC_URI += " \
    file://add-default-root-pass.patch \
    "
