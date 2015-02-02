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

SRC_URI = "http://www.secdev.org/projects/scapy/files/scapy-${PV}.zip"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils

RDEPENDS_${PN} = "\
  python-netclient \
  python-netserver \
"

SRC_URI[md5sum] = "46f4ef88b676daebd3053bbd2ee16425"
SRC_URI[sha256sum] = "9a4bacfca772f385d71bad43b1676d5f5b380c8bceb24443dcda29bc13108262"
