#!/bin/ksh -p
#
# StarshipOS Copyright (c) 2015-2025. R.A. James
#
OS=`uname`

# pick up the compiled class files.
if [ -z "${TESTCLASSES}" ]; then
  CP="."
else
  CP="${TESTCLASSES}"
fi

if [ $OS != Linux ]
then
    exit 0
fi

if [ -z "${TESTJAVA}" ] ; then
   JAVA_HOME=../../../../../build/solaris-sparc
else
   JAVA_HOME=$TESTJAVA
fi

$JAVA_HOME/bin/java ${TESTVMOPTS} \
    -cp "${CP}" -Xcheck:jni JNICheck | grep -v SIG | grep -v Signal | grep -v Handler | grep -v jsig | grep -v CallStatic > "${CP}"/log.txt

# any messages logged may indicate a failure.
if [ -s "${CP}"/log.txt ]; then
    echo "Test failed"
    exit 1
fi

echo "Test passed"
exit 0
