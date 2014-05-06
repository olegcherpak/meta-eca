FILESEXTRAPATHS_append := ":${THISDIR}/files"

SRC_URI_append = " \
           file://0001-No-arpd-please.patch \
"

# Compile also misc directory (to get ss prog)
#
EXTRA_OEMAKE_append = " SUBDIRS='lib tc ip misc'"
