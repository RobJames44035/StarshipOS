#!/bin/sh

#
# StarshipOS Copyright (c) 2016-2025. R.A. James
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

echo "TESTCLASSES=${TESTCLASSES}"

mkdir -p hidden
${COMPILEJAVA}/bin/javac ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} \
    -d hidden ${TESTSRC}/../ExampleForClassPath.java

mkdir -p classes
${COMPILEJAVA}/bin/javac ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} \
    -d classes ${TESTSRC}/../InstrumentationHandoff.java

echo "Manifest-Version: 1.0" > Agent.mf
echo "Class-Path: hidden/" >> Agent.mf
echo "Premain-Class: InstrumentationHandoff" >> Agent.mf

${TESTJAVA}/bin/jar ${TESTTOOLVMOPTS} cvfm Agent.jar \
    Agent.mf -C classes InstrumentationHandoff.class
