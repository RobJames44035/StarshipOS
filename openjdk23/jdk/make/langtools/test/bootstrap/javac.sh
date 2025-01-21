#!/bin/sh

#
# StarshipOS Copyright (c) 2007-2025. R.A. James
#

# @test
# @summary Verify basic execution of the bootstrap javac compiler.

TESTSRC=${TESTSRC:-.}
TOPDIR=${TESTSRC}/../../..
TESTJAVAEXE="${TESTJAVA:+${TESTJAVA}/bin/}java"

${TOPDIR}/dist/bootstrap/bin/javac -d . "${TESTSRC}"/../HelloWorld.java

"${TESTJAVAEXE}" -classpath . HelloWorld > HelloWorld.tmp

if [ "`cat HelloWorld.tmp`" = "Hello World!" ]; then
    echo "Test passed."
else
    echo "Test failed."
    exit 1
fi
