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
echo "CLASSPATH=${CLASSPATH}"

JAVAC="${COMPILEJAVA}/bin/javac -g"
JAR="${COMPILEJAVA}/bin/jar"

mkdir agentclasses
touch agent.mf
echo "Manifest-Version: 1.0" >> agent.mf
echo "Boot-Class-Path: agent.jar" >> agent.mf
echo "Premain-Class: p.BootstrapClassPathAgent" >> agent.mf

cp ${TESTSRC}/BootstrapClassPathAgent.java BootstrapClassPathAgent.java
${JAVAC} ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d agentclasses BootstrapClassPathAgent.java
echo "JAR=${JAR}"
${JAR} ${TESTTOOLVMOPTS} cvfm agent.jar agent.mf -C agentclasses .
rm -f BootstrapClassPathAgent.class BootstrapClassPathAgent.java
