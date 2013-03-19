PRINC := "${@int(PRINC) + 1}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "\
	file://name-owner-changed.patch \
	file://set-debug-level.patch \
"
