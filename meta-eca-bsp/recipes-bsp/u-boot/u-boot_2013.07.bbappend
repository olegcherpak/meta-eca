# Find "files" directory
FILESEXTRAPATHS := "${THISDIR}/files"

SRC_URI_append_beagleboard += " \
	file://no_delay.patch \
"
