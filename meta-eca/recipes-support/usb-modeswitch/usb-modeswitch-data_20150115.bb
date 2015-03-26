SUMMARY = "Data files for usbmodeswitch"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit allarch

SRC_URI = "http://www.draisberghof.de/usb_modeswitch/${BP}.tar.bz2;name=tarball \
	http://www.draisberghof.de/usb_modeswitch/device_reference.txt;name=devref"
SRC_URI[tarball.md5sum] = "662bcd56a97e560ea974bc710822de51"
SRC_URI[tarball.sha256sum] = "90549f589835a68279369c3dc0d47eb7338ee3bad09d737e7b85e1ab15bd2d8b"
SRC_URI[devref.md5sum] = "080bac4145f002466b1cbd1b973bba44"
SRC_URI[devref.sha256sum] = "07d32333eda6b8c19572490e01ab6e7b6912fb07a4b2f0f491ddb80ad7eceed3"

do_install() {
    oe_runmake install DESTDIR=${D}
    cp ${WORKDIR}/device_reference.txt ${D}/${datadir}/usb_modeswitch
}

RDEPENDS_${PN} = "usb-modeswitch (>= 2.2.0)"
FILES_${PN} += "${base_libdir}/udev/rules.d/ \
                ${datadir}/usb_modeswitch"
