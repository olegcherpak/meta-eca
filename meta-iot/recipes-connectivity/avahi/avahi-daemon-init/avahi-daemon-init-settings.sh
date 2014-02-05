#!/bin/sh

KERNEL_VERSION=`uname -r | cut -d . -f 1-2`
MAJOR=`echo $KERNEL_VERSION | cut -d . -f 1`
MINOR=`echo $KERNEL_VERSION | cut -d . -f 2`

if [ "${MAJOR}.`printf '%02d' $MINOR`" \< "3.09" ]; then
    # avahi might not start because SO_REUSEPORT might be missing during
    # runtime. The symbol is only available from kernel 3.9 onwards.
    AVAHI_CONF=/etc/avahi/avahi-daemon.conf
    if [ -f $AVAHI_CONF ]; then
	sed -i -e "s|^#disallow-other-stacks=.*|disallow-other-stacks=yes|" $AVAHI_CONF
    else
	echo "[server]" > $AVAHI_CONF
	echo "disallow-other-stacks=yes" >> $AVAHI_CONF
    fi
fi

exit 0

