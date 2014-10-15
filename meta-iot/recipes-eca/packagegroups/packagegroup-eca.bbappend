# IoT specific packages for ECA

PACKAGES_append = "\
    ${@base_contains('DISTRO_FEATURES', 'internet-of-things', 'packagegroup-eca-iot', '', d)} \
"

THE_THING_SYSTEM="\
    steward-init \
    steward \
    tts-nodejs \
    ${@base_contains('DISTRO_FEATURES', 'kiosk', 'tts-starter', '', d)} \
"

SUMMARY_packagegroup-eca-iot = "Internet of Things support for ECA"
RDEPENDS_packagegroup-eca-iot = "\
    ${THE_THING_SYSTEM} \
    avahi-daemon-init \
"
