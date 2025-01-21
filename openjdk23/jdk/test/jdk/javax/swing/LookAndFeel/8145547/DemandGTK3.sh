#!/bin/ksh -p

#
# StarshipOS Copyright (c) 2016-2025. R.A. James
#


#   @test
#   @summary  Try to force GTK3.
#
#   @key headful
#   @bug 8156128 8212903
#   @compile ProvokeGTK.java
#   @requires os.family == "linux"
#   @run shell/timeout=400 DemandGTK3.sh

#
# Note that we depend on
# strace in the PATH
# /sbin/ldconfig (which may be is not in PATH)
# It is true for OEL 7 and Ubuntu 14, 16
# but may fail in future. Save tomorrow for tomorrow.
#

which strace
if [ $?  -ne 0 ]
then
    echo "Please provide strace: \"which strace\" failed."
    exit 1
fi

HAVE_3=`/sbin/ldconfig -v 2>/dev/null | grep libgtk-3.so | wc -l`


if [ "${HAVE_3}" = "0" ]
then
    echo "No GTK 3 library found, do nothing"
    exit 0
else
    echo "There is GTK 3 library: we should use it"
    strace -o strace.log -fe open,openat ${TESTJAVA}/bin/java  -cp ${TESTCLASSPATH}  -Djdk.gtk.version=3 ProvokeGTK
    EXECRES=$?
    grep  'libgtk-3.*=\ *[0-9]*$' strace.log > logg
fi

if [ ${EXECRES}  -ne 0 ]
then
    echo "java execution failed for unknown reason, see logs"
    exit 2
fi

cat logg
if [  -s logg ]
then
    echo "Success."
    exit 0
else
    echo "Failed. Examine logs."
    exit 3
fi

