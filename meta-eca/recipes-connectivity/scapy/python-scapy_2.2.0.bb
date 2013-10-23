DESCRIPTION = "Scapy is a powerful interactive packet manipulation tool, \
packet generator, network scanner, network discovery, packet sniffer, etc. \
It can for the moment replace hping, 85% of nmap, arpspoof, arp-sk, arping, \
tcpdump, tethereal, p0f, ...."
SECTION = "devel/python"
HOMEPAGE = "http://www.secdev.org/projects/scapy/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://bin/scapy;beginline=3;endline=18;md5=a5be896f88f8396346f67f7a8878ee09"
PRIORITY = "optional"
SRCNAME = "scapy"
PR = "ml2"

SRC_URI = "http://www.secdev.org/projects/scapy/files/scapy-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils

RDEPENDS_${PN} = "\
  python-netclient \
  python-netserver \
"

SRC_URI[md5sum] = "406990bd8da1f4958b354b4b6fc4b3eb"
SRC_URI[sha256sum] = "c5363b224df0efbd78d7dc4d8a518e5518b2e7affc2e5f1fcecd4efa3ab815af"
