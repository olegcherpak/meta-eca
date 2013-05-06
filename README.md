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
revision: 07ef66f079ec648c22c14610011dd79b0af1c424

URI: git://git.openembedded.org/meta-openembedded
branch: master
revision: 3c47bd59d0315c98364a175a686e8a4b8ba66867

Using the above git sha's and master meta-eca, bitbaking eca-image is
known to work.

Build a QEMU image including ECA components
-------------------------------------------

You can build a QEMU image including ECA components using the
following steps:

1. Run the following command:

   > $ source poky/oe-init-build-env

2. Add meta-eca path to COREBASE/build/conf/bblayers.conf file.

3. Add meta-openembedded/meta-systemd path to COREBASE/build/conf/bblayers.conf
   file.

4. Add meta-openembedded/meta-oe path to COREBASE/build/conf/bblayers.conf file.

5. Add meta-openembedded/meta-networking path to
   COREBASE/build/conf/bblayers.conf file.

6. Set MACHINE ??= "qemux86" in COREBASE/build/conf/local.conf file to build
   for an emulated IA-32 instruction-set machine respectively.

7. Set DISTRO ?= "eca" in COREBASE/build/conf/local.conf file.
   If you want a bleeding edge versions of ConnMan, oFono, Bluez5 and Neard,
   then you can set the DISTRO to "eca-bleeding"

8. Mask out bluez4 as we want to use bluez5, set BBMASK in your local.conf file
   BBMASK = "meta/recipes-connectivity/bluez/bluez4*|\
   meta-openembedded/meta-systemd/oe-core/recipes-connectivity/bluez/bluez4*"

9. Optional: In COREBASE/build/conf/local.conf file, you may uncomment
   BB_NUMBER_THREADS = "4" and PARALLEL_MAKE = "-j 4" if you build on a
   quad-core machine.

10. Build eca-image

   > $ bitbake eca-image

11. Run the emulator:

   > for qemux86:
   > $ PATH_TO_POKY/poky/scripts/runqemu qemux86 eca-image
