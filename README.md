meta-eca, the Yocto layer for communication appliances
======================================================

This layer's purpose is to add Embedded Communication Appliance (ECA) support
when used with Poky.

Please see the MAINTAINERS file for information on contacting the maintainers
of this layer, as well as instructions for submitting patches.

Layer Dependencies
------------------

URI: git://git.yoctoproject.org/poky
branch: master
revision: ...

URI: git://git.openembedded.org/meta-openembedded
branch: master
revision: ...

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

7. Add
BBMASK = "meta-systemd/meta-efl|meta-systemd/meta-gnome|\
meta-systemd/meta-multimedia|meta-systemd/meta-oe/recipes-support|\
meta-gnome/recipes-gnome"
in COREBASE/build/conf/local.conf file.

8. Set DISTRO ?= "eca" in COREBASE/build/conf/local.conf file.

9. Optional: In COREBASE/build/conf/local.conf file, you may uncomment
   BB_NUMBER_THREADS = "4" and PARALLEL_MAKE = "-j 4" if you build on a
   quad-core machine.

10. Build eca-image

   > $ bitbake eca-image

11. Run the emulator:

   > for qemux86:
   > $ PATH_TO_POKY/poky/scripts/runqemu qemux86 eca-image
