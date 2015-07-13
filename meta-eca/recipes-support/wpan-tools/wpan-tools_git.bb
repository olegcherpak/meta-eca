SUMMARY = "Userspace tools for Linux IEEE 802.15.4 stack"
HOMEPAGE = "http://wpan.cakelab.org/releases/"
DESCRIPTION = "This is a set of utils to manage the Linux WPAN stack via \
netlink interface. This requires recent kernel with nl802154 interface."

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4c90a0ebb6b0b86b0ab38254fc853b57"

DEPENDS = "libnl"

PV = "0.4+git${SRCPV}"
SRC_URI = "git://github.com/linux-wpan/wpan-tools.git"
SRCREV = "74a37056c596f73f1aed2d89973c3cb87ba43cb3"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
