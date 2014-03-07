DESCRIPTION = "Packagegroup for ECA desktop packages"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"
DEPENDS = "virtual/kernel"
PR = "r2"

inherit packagegroup

PACKAGES = "\
    ${@base_contains('DISTRO_FEATURES', 'desktop', 'packagegroup-eca-desktop', '', d)} \
"

# Graphical desktop support
# pcmanfm doesn't work on mips
FILEMANAGER ?= "pcmanfm"
FILEMANAGER_mips ?= ""
BROWSER = "chromium"
DESKTOP_APPS="\
    leafpad \
    gaku \
    x11vnc \
    matchbox-terminal \
    sato-screenshot \
    ${FILEMANAGER} \
    ${BROWSER} \
"

SUMMARY_packagegroup-eca-desktop = "ECA graphical desktop support"
RDEPENDS_packagegroup-eca-desktop = "\
    matchbox-desktop \
    matchbox-session-sato \
    matchbox-keyboard \
    matchbox-keyboard-applet \
    matchbox-keyboard-im \
    matchbox-config-gtk \
    xcursor-transparent-theme \
    sato-icon-theme \
    settings-daemon \
    gtk-sato-engine \
    shutdown-desktop \
    libsdl \
    connman-gnome \
    udev-extraconf \
    ${DESKTOP_APPS} \
"
