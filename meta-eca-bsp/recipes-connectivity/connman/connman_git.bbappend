# In ARM tweak the CFLAGS and remove cast-align because the GCC 4.7.2
# gives "cast increases required alignment of target type" warning
# which aborts connman compilation
CFLAGS_prepend_arm = " -Wno-error=cast-align "
