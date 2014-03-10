# Recipe for Pulseaudio 4.0 that uses bluez5

require recipes-multimedia/pulseaudio/pulseaudio.inc

PV="4.0"

SRC_URI = "http://freedesktop.org/software/pulseaudio/releases/pulseaudio-${PV}.tar.xz \
           file://volatiles.04_pulse"

SRC_URI[md5sum] = "591f211db2790a7e4d222f2dc6858db3"
SRC_URI[sha256sum] = "35ceb36bb1822fe54f0b5e4863b4f486769fdfb8ff2111f01fd8778928f9cdae"

do_compile_prepend() {
    mkdir -p ${S}/libltdl
    cp ${STAGING_LIBDIR}/libltdl* ${S}/libltdl
}

PACKAGECONFIG[bluez] = "--enable-bluetooth, --disable-bluetooth, bluez5"
