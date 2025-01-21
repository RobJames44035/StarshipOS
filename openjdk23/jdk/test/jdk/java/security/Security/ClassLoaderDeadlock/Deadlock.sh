#!/bin/sh

#
# StarshipOS Copyright (c) 2003-2025. R.A. James
#


# @test
# @bug 4944382
# @summary make sure we do not deadlock loading signed JAR with getInstance()
# @author Andreas Sterbenz
# @build Deadlock
# @run shell/timeout=30 Deadlock.sh

# set platform-dependent variables
OS=`uname -s`
case "$OS" in
  Linux )
    PATHSEP=":"
    FILESEP="/"
    ;;
  Darwin )
    PATHSEP=":"
    FILESEP="/"
    ;;
  AIX )
    PATHSEP=":"
    FILESEP="/"
    ;;
  CYGWIN* )
    PATHSEP=";"
    FILESEP="/"
    ;;
  Windows* )
    PATHSEP=";"
    FILESEP="\\"
    ;;
  * )
    echo "Unrecognized system!"
    exit 1;
    ;;
esac

JAVA="${TESTJAVA}${FILESEP}bin${FILESEP}java"

${JAVA} ${TESTVMOPTS} ${TESTJAVAOPTS} -cp "${TESTCLASSES}${PATHSEP}${TESTSRC}${FILESEP}Deadlock.jar" Deadlock

