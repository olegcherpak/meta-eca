meta-eca, the Yocto layer for communication appliances
======================================================

This layer's purpose is to add Embedded Connectivity Appliance (ECA) support
when used with Poky packagegroup base.

Please see the MAINTAINERS file for information on contacting the maintainers
of this layer, as well as instructions for submitting patches.

Layer Dependencies
------------------

URI: git://git.yoctoproject.org/poky
branch: master
revision: 4d2ac6f6df2b3ef98699dd4f7afadb2d994222bb

URI: git://git.openembedded.org/meta-openembedded
branch: master
revision: be2a2437005087b3f8d43a09ddc69991ed81e110

Using the above git sha's and master revisions, bitbaking eca-image is
known to work. Using head versions should work also just fine.


Build a QEMU image including ECA components
-------------------------------------------

You can build a QEMU image including ECA components using the
following steps:

1. Run the following command:

   > $ source poky/oe-init-build-env

2. Add meta-eca/meta-eca path to COREBASE/build/conf/bblayers.conf file.

3. Add meta-eca/meta-eca-bsp path to COREBASE/build/conf/bblayers.conf file.

4. Add meta-openembedded/meta-systemd path to COREBASE/build/conf/bblayers.conf
   file.

5. Add meta-openembedded/meta-oe path to COREBASE/build/conf/bblayers.conf file.

6. Add meta-openembedded/meta-networking path to
   COREBASE/build/conf/bblayers.conf file.

7. Add meta-openembedded/meta-ruby path to COREBASE/build/conf/bblayers.conf
   file.

8. Set MACHINE ??= "qemux86" in COREBASE/build/conf/local.conf file to build
   for an emulated IA-32 instruction-set machine respectively.

9. Set DISTRO ?= "eca" in COREBASE/build/conf/local.conf file.
   If you want a bleeding edge versions of ConnMan, oFono, Bluez5 and Neard,
   then you can set the DISTRO to "eca-bleeding"

10. Mask out bluez4 as we want to use bluez5, set BBMASK in your local.conf file
   also ignore ofono recipe as ECA has own one.
   BBMASK = "meta/recipes-connectivity/bluez/bluez4*|\
   meta-openembedded/meta-systemd/oe-core/recipes-connectivity/bluez/bluez4*|\
   meta-openembedded/meta-systemd/oe-core/recipes-connectivity/ofono/ofono*"

11. Optional: In COREBASE/build/conf/local.conf file, you may uncomment
   BB_NUMBER_THREADS = "4" and PARALLEL_MAKE = "-j 4" if you build on a
   quad-core machine.

12. Build eca-image

   > $ bitbake eca-image

13. Run the emulator:

   > for qemux86:
   > $ runqemu qemux86 eca-image

14. Set the password for root user
    The default password for root user is "root". You should change
    that to something that only you know. If connecting to the host
    using browser (use http://eca.local/ address), the web-ui
    forces you to change the root password and it also asks you to
    set the username/password for the web management UI.
    If connecting to host using ssh, you need to set the root password
    manually using "passwd" command.

15. Connecting to the device
    After you have booted the appliance, you can connect to it using
    ethernet or wifi. If you have wifi card or usb dongle installed
    in appliance, you should see access point called "eca-aabbccddeeff"
    (this is just an example) in your wifi scans.
    You can connect to that AP, the default passphrase in this case
    would be "aabbccddeeff".
    Connection can be done either via "ssh root@eca.local" or via
    browser http://eca.local
