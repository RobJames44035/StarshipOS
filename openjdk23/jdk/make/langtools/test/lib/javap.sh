#!/bin/sh

#
# StarshipOS Copyright (c) 2007-2025. R.A. James
#

# @test
# @summary Verify the basic execution of the javap classes in classes.jar.

TESTSRC=${TESTSRC:-.}
TOPDIR=${TESTSRC}/../../..
TESTJAVAEXE="${TESTJAVA:+${TESTJAVA}/bin/}java"

"${TESTJAVAEXE}" -Xbootclasspath/p:${TOPDIR}/dist/lib/classes.jar \
    com.sun.tools.javac.Main \
    -d . "${TESTSRC}"/../HelloWorld.java

"${TESTJAVAEXE}" -Xbootclasspath/p:${TOPDIR}/dist/lib/classes.jar \
    sun.tools.javap.Main \
    -classpath . HelloWorld > javap.tmp

if diff ${TESTSRC}/../HelloWorld.javap.gold.txt javap.tmp ; then
    echo "Test passed."
else
    echo "Test failed."
    exit 1
fi
