#!/bin/sh
#
# StarshipOS Copyright (c) 2015-2025. R.A. James
#

#
#   @test
#   @bug        8138817 8152971
#   @summary    Tests that there are no JNI warnings about local references.
#   @compile LoadFontsJNICheck.java
#   @run shell/timeout=300 LoadFontsJNICheck.sh
#
OS=`uname`

# pick up the compiled class files.
if [ -z "${TESTCLASSES}" ]; then
  CP="."
else
  CP="${TESTCLASSES}"
fi

if [ -z "${TESTJAVA}" ] ; then
   JAVACMD=java
else
   JAVACMD=$TESTJAVA/bin/java
fi

$JAVACMD ${TESTVMOPTS} \
    -cp "${CP}" -Xcheck:jni LoadFontsJNICheck | grep "local refs"  > "${CP}"/log.txt

# any messages logged may indicate a failure.
if [ -s "${CP}"/log.txt ]; then
    echo "Test failed"
    cat "${CP}"/log.txt
    exit 1
fi

echo "Test passed"
exit 0
