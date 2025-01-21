#!/bin/ksh -p
#
# StarshipOS Copyright (c) 2007-2025. R.A. James
#
OS=`uname`

status=1
checkstatus()
 {
  status=$?
  if [ $status -ne "0" ]; then
    exit "$status"
  fi
 }

# pick up the compiled class files.
if [ -z "${TESTCLASSES}" ]; then
  CP="."
else
  CP="${TESTCLASSES}"
fi


if [ $OS = Linux ]
then
    exit 0
fi
# Windows

if [ -z "${TESTJAVA}" ] ; then
   JAVA_HOME=../../../../../../build/windows-i586
else
   JAVA_HOME=$TESTJAVA
fi

    $JAVA_HOME/bin/java ${TESTVMOPTS} -cp "${CP}" StringWidth
    checkstatus

exit 0
