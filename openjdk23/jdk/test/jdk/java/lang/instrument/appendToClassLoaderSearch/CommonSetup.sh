#!/bin/sh

#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#


#
# Common setup for unit tests. Setups up the following variables:
#
# PS - path sep.
# FS - file sep.
# JAVA - java cmd.
# JAVAC - javac cmd.
# JAR - jar cmd.

OS=`uname -s`
case "$OS" in
  Linux )
    PS=":"
    FS="/"
    ;;
  Darwin )
    PS=":"
    FS="/"
    ;;
  AIX )
    PS=":"
    FS="/"
    ;;
  Windows*)
    PS=";"
    OS="Windows"
    FS="\\"
    ;;
  CYGWIN*)
    PS=";"
    OS="Windows"
    FS="\\"
    isCygwin=true
    ;;
  * )
    echo "Unrecognized system!"
    exit 1;
    ;;
esac

if [ "${TESTJAVA}" = "" ]
then
  echo "TESTJAVA not set.  Test cannot execute.  Failed."
  exit 1
fi

if [ "${COMPILEJAVA}" = "" ]
then
  COMPILEJAVA="${TESTJAVA}"
fi
echo "COMPILEJAVA=${COMPILEJAVA}"

if [ "${TESTSRC}" = "" ]
then
  echo "TESTSRC not set.  Test cannot execute.  Failed."
  exit 1
fi

if [ "${TESTCLASSES}" = "" ]
then
  echo "TESTCLASSES not set.  Test cannot execute.  Failed."
  exit 1
fi

JAVA="${TESTJAVA}/bin/java"
JAVAC="${COMPILEJAVA}/bin/javac"
JAR="${COMPILEJAVA}/bin/jar"

