#!/bin/bash
#
# StarshipOS Copyright (c) 2018-2025. R.A. James
#

VECTORTESTS_HOME="$(pwd)"
JDK_SRC_HOME="./../../../../../"
JAVA="${JAVA_HOME}/bin/java"
JAVAC="${JAVA_HOME}/bin/javac"
BUILDLOG_FILE="./build.log"
SPP_CLASSNAME="build.tools.spp.Spp"
# Windows: Classpath Separator is ';'
# Linux: ':'
SEPARATOR=":"
TYPEPREFIX=""
TEMPLATE_FILE="unit_tests.template"
TEST_ITER_COUNT=100

PERF_TEMPLATE_FILE="perf_tests.template"
PERF_SCALAR_TEMPLATE_FILE="perf_scalar_tests.template"
PERF_DEST="benchmark/src/main/java/benchmark/jdk/incubator/vector/"

function Log () {
  if [ $1 == true ]; then
    echo "$2"
  fi
  echo "$2" >> $BUILDLOG_FILE
}

function LogRun () {
  if [ $1 == true ]; then
    echo -ne "$2"
  fi
  echo -ne "$2" >> $BUILDLOG_FILE
}

# Determine which delimiter to use based on the OS.
# Windows uses ";", while Unix-based OSes use ":"
uname_s=$(uname -s)
VECTORTESTS_HOME_CP=$VECTORTESTS_HOME
if [ "x${VAR_OS_ENV}" == "xwindows.cygwin" ]; then
  VECTORTESTS_HOME_CP=$(cygpath -pw $VECTORTESTS_HOME)
fi

if [ "$uname_s" == "Linux" ] || [ "$uname_s" == "Darwin" ]; then
  SEPARATOR=":"
else
  SEPARATOR=";"
fi
