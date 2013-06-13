meta-eca, the Yocto layer for communication appliances
======================================================

This layer's purpose is to add Embedded Communication Appliance (ECA) support
when used with Poky packagegroup base.

Please see the MAINTAINERS file for information on contacting the maintainers
of this layer, as well as instructions for submitting patches.

Layer Dependencies
------------------

URI: git://git.yoctoproject.org/poky
branch: master
revision: 697f74988a50a992c25853272aaf0963b2b3cf28

URI: git://git.openembedded.org/meta-openembedded
branch: master
revision: 17ace92714bccf64d0d9fcefaabe3067f59fdf0a

Using the above git sha's and master meta-eca, bitbaking eca-image is
known to work. Using head versions should work also just fine.

Build a QEMU image including ECA components
-------------------------------------------

You can build a QEMU image including ECA components using the
following steps:

1. Run the following command:

   > $ source poky/oe-init-build-env

2. Add meta-eca path to COREBASE/build/conf/bblayers.conf file.

3. Add meta-eca/meta-eca-bsp path to COREBASE/build/conf/bblayers.conf file.

4. Add meta-openembedded/meta-systemd path to COREBASE/build/conf/bblayers.conf
   file.

5. Add meta-openembedded/meta-oe path to COREBASE/build/conf/bblayers.conf file.

6. Add meta-openembedded/meta-networking path to
   COREBASE/build/conf/bblayers.conf file.

7. Set MACHINE ??= "qemux86" in COREBASE/build/conf/local.conf file to build
   for an emulated IA-32 instruction-set machine respectively.

8. Set DISTRO ?= "eca" in COREBASE/build/conf/local.conf file.
   If you want a bleeding edge versions of ConnMan, oFono, Bluez5 and Neard,
   then you can set the DISTRO to "eca-bleeding"

9. Mask out bluez4 as we want to use bluez5, set BBMASK in your local.conf file
   BBMASK = "meta/recipes-connectivity/bluez/bluez4*|\
   meta-openembedded/meta-systemd/oe-core/recipes-connectivity/bluez/bluez4*"

10. Optional: In COREBASE/build/conf/local.conf file, you may uncomment
   BB_NUMBER_THREADS = "4" and PARALLEL_MAKE = "-j 4" if you build on a
   quad-core machine.

11. Build eca-image

   > $ bitbake eca-image

12. Run the emulator:

   > for qemux86:
   > $ runqemu qemux86 eca-image

13. Set the password for root user
    The default password for root user is "root". You should change
    that to something that only you know. If connecting to the host
    using browser (use http://eca.local/ address), the web-ui
    forces you to change the root password and it also asks you to
    set the username/password for the web management UI.
    If connecting to host using ssh, you need to set the root password
    manually using "passwd" command.
