# Recipe for Pulseaudio that uses bluez5

PACKAGECONFIG = "${@base_contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
                   ${@base_contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)} \
                   ${@base_contains('DISTRO_FEATURES', 'zeroconf', 'avahi', '', d)} \
                   ${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"

require recipes-multimedia/pulseaudio/pulseaudio.inc

PV="5.0"

SRC_URI = "http://freedesktop.org/software/pulseaudio/releases/pulseaudio-${PV}.tar.xz \
           file://0001-configure.ac-Check-only-for-libsystemd-not-libsystem.patch \
           file://volatiles.04_pulse"

SRC_URI[md5sum] = "c43749838612f4860465e83ed62ca38e"
SRC_URI[sha256sum] = "99c13a8b1249ddbd724f195579df79484e9af6418cecf6a15f003a7f36caf939"

do_compile_prepend() {
    mkdir -p ${S}/libltdl
    cp ${STAGING_LIBDIR}/libltdl* ${S}/libltdl
}

