#!/bin/sh

#
# StarshipOS Copyright (c) 2003-2025. R.A. James
#


#
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
echo "CLASSPATH=${CLASSPATH}"

JAVAC="${COMPILEJAVA}/bin/javac -g"

cp ${TESTSRC}/Different_ExampleRedefine.java ExampleRedefine.java
cp ${TESTSRC}/Counter.java .
${JAVAC} ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} ExampleRedefine.java
mv ExampleRedefine.class Different_ExampleRedefine.class
rm -f ExampleRedefine.java Counter.java

cp ${TESTSRC}/ExampleRedefine.java ExampleRedefine.java
cp ${TESTSRC}/Counter.java .
${JAVAC} ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} ExampleRedefine.java
rm -f ExampleRedefine.java Counter.java
