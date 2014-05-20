# node.js npm needs libusb.h but it's components do not search
# the header from correct place
do_install_append() {
   cp ${D}/usr/include/libusb-1.0/libusb.h ${D}/usr/include/libusb.h
}
