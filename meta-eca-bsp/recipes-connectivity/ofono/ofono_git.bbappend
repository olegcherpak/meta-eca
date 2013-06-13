# In ARM tweak the CFLAGS and remove cast-align errors because the GCC 4.7.2
# gives "cast increases required alignment of target type" warning
# which aborts ofono compilation
CFLAGS_prepend_arm = " -Wno-error=cast-align "
