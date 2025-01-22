#!/bin/ksh -p
#
# StarshipOS Copyright (c) 2020-2025. R.A. James
#
OS=`uname`

if [ "${TESTJAVA}" = "" ]
then
  echo "TESTJAVA not set.  Test cannot execute.  Failed."
  exit 1
fi

# pick up the compiled class files.
if [ -z "${TESTCLASSES}" ]; then
  CP="."
  $TESTJAVA/bin/javac GetImageJNICheck.java
else
  CP="${TESTCLASSES}"
fi

$TESTJAVA/bin/java ${TESTVMOPTS} \
    -cp "${CP}" -Xcheck:jni GetImageJNICheck | grep ReleasePrimitiveArrayCritical > "${CP}"/log.txt

#if [ $? -ne 0 ]
#    then
#      echo "Test fails: exception thrown!"
#      exit 1
#fi

# any messages logged indicate a failure.
if [ -s "${CP}"/log.txt ]; then
    echo "Test failed"
    cat "${CP}"/log.txt
    exit 1
fi

echo "Test passed"
exit 0
