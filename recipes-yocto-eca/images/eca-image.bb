include recipes-yocto-eca/images/eca-image.inc

PV = "0.2"
PR = "r1"

IMAGE_INSTALL_append = " packagegroup-core"

IMAGE_INSTALL_append = " packagegroup-eca-core"

