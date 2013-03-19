PRINC := "${@int(PRINC) + 3}"

inherit systemd

EXTRA_OECONF = "\
  --disable-gstreamer \
  --enable-usb \
  --enable-tools \
  --enable-bccmd \
  --enable-hid2hci \
  --enable-dfutool \
  --disable-hidd \
  --enable-pand \
  --enable-dund \
  --disable-cups \
  --enable-test \
  --enable-datafiles \
  --with-udevdir=${base_libdir}/udev \
  --with-udevrulesdir=${nonarch_base_libdir}/udev/rules.d \
  --with-systemdunitdir=${systemd_unitdir}/system/ \
"

SYSTEMD_PACKAGES = "${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd = "bluetooth.service"

SYSTEMD_AUTO_ENABLE = "enable"

do_configure_append () {
	#udev_get_dev_path(), udev_get_sys_path(), udev_get_run_path()
	#systemd does not allow to configure any of these filesystem paths
	#udev is included in systemd
	sed -i 's:udev_get_sys_path(udev):"/sys":' tools/hid2hci.c
}

PACKAGES += " ${PN}-systemd"
