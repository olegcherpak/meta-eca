PRINC := "${@int(PRINC) + 1}"

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
