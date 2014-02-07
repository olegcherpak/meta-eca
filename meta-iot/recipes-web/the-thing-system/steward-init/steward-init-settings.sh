#!/bin/sh

if [ -z "$THE_THING_SYSTEM" ]; then
    THE_THING_SYSTEM=/opt/TheThingSystem
    export THE_THING_SYSTEM
fi

STEWARD_DIR=${THE_THING_SYSTEM}/steward
STEWARD_SETTINGS=$STEWARD_DIR/db/server.key

RET=0

if [ -s $STEWARD_SETTINGS ]; then
    exit 0
fi

# Change the port of the eca-web so that steward web-ui can live in
# port 80. Eca-web will be moved to HTTP port 8080.
sed -i 's/PORT=80$/PORT=8080/' /etc/eca-web/config

if [ ! -d $STEWARD_DIR/sandbox ]; then
    mkdir -p $STEWARD_DIR/sandbox
fi
if [ ! -d $STEWARD_DIR/db ]; then
    mkdir -p $STEWARD_DIR/db
fi

if [ -z "$NODE_PATH" ]; then
    NODE_PATH=${THE_THING_SYSTEM}/steward
    export NODE_PATH
fi

cd $THE_THING_SYSTEM/steward

echo -n "Creating server key..."
rm -f ${STEWARD_DIR}/sandbox/server.crt ${STEWARD_DIR}/sandbox/server.sha1

node <<EOF
require('x509-keygen').x509_keygen({ subject  : '/CN=steward'
                      , keyfile  : '${STEWARD_DIR}/db/server.key'
                      , certfile : '${STEWARD_DIR}/sandbox/server.crt'
                      , sha1file : '${STEWARD_DIR}/sandbox/server.sha1'
                      , alternates : [ 'DNS:' + require('os').hostname(), 'DNS:eca.local' ]
                      , destroy  : false }, function(err, data) {
  if (err) return console.log('keypair generation error: ' + err.message);

  console.log('keypair generated.');
});
EOF

if [ -f ${STEWARD_DIR}/db/server.key ]; then
    chmod 400 ${STEWARD_DIR}/db/server.key
    chmod 444 ${STEWARD_DIR}/sandbox/server.crt
    chmod 444 ${STEWARD_DIR}/sandbox/server.sha1
else
    rm -f ${STEWARD_DIR}/db/server.key ${STEWARD_DIR}/sandbox/server.crt \
	${STEWARD_DIR}/sandbox/server.sha1
    echo "unable to create self-signed server certificate" 1>&2
    RET=1
fi

exit $RET
