PRINC := "${@int(PRINC) + 1}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
	file://activation-helper-bin.patch \
	file://activation-helper.patch \
"
