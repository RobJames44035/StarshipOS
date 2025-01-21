#!/bin/sh

#
# StarshipOS Copyright (c) 2007-2025. R.A. James
#

# @test
# @summary Verify basic execution of the javac classes in classes.jar.

TESTSRC=${TESTSRC:-.}
TOPDIR=${TESTSRC}/../../..
TESTJAVAEXE="${TESTJAVA:+${TESTJAVA}/bin/}java"

"${TESTJAVAEXE}" -Xbootclasspath/p:${TOPDIR}/dist/lib/classes.jar \
    com.sun.tools.javac.Main \
    -d . "${TESTSRC}"/../HelloWorld.java

"${TESTJAVAEXE}" -classpath . HelloWorld > HelloWorld.tmp

if [ "`cat HelloWorld.tmp`" = "Hello World!" ]; then
    echo "Test passed."
else
    echo "Test failed."
    exit 1
fi
