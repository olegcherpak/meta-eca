# Find "files" directory
FILESEXTRAPATHS := "${THISDIR}/files"

SRC_URI_append_beagleboard += " \
	file://0001-beagle-Use-ttyO2-as-a-console-as-that-it-should-be-i.patch \
	file://no_delay.patch \
"
