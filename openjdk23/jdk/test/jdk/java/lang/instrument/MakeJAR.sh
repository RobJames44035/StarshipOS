#!/bin/sh

#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

if [ "${TESTSRC}" = "" ]
then
  echo "TESTSRC not set.  Test cannot execute.  Failed."
  exit 1
fi
echo "TESTSRC=${TESTSRC}"

if [ "${TESTJAVA}" = "" ]
then
  echo "TESTJAVA not set.  Test cannot execute.  Failed."
  exit 1
fi
echo "TESTJAVA=${TESTJAVA}"

if [ "${COMPILEJAVA}" = "" ]
then
  COMPILEJAVA="${TESTJAVA}"
fi
echo "COMPILEJAVA=${COMPILEJAVA}"

if [ "${TESTCLASSES}" = "" ]
then
  echo "TESTCLASSES not set.  Test cannot execute.  Failed."
  exit 1
fi

JAVAC="${COMPILEJAVA}/bin/javac -g"
JAR="${COMPILEJAVA}/bin/jar"

cp ${TESTSRC}/InstrumentationHandoff.java InstrumentationHandoff.java
${JAVAC} ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} InstrumentationHandoff.java
${JAR} ${TESTTOOLVMOPTS} cvfm $1.jar ${TESTSRC}/$1.mf InstrumentationHandoff.class
rm -f InstrumentationHandoff.class InstrumentationHandoff.java
