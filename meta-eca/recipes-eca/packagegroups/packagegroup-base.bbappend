# We want to use bluez5
RDEPENDS_packagegroup-base-bluetooth = "\
    bluez5 \
    ${@base_contains('COMBINED_FEATURES', 'alsa', 'libasound-module-bluez', '',d)} \
    "
