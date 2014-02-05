# TheThingSystem steward needs avahi compat headers
EXTRA_OECONF += "--enable-compat-libdns_sd"

# node.js npm needs dns_sd.h but it's components do not search
# the header from correct place
do_install_append() {
   cp ${D}/usr/include/avahi-compat-libdns_sd/dns_sd.h ${D}/usr/include/dns_sd.h
}
