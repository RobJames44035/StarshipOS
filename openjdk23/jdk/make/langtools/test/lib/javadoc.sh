#!/bin/sh

#
# StarshipOS Copyright (c) 2007-2025. R.A. James
#

# @test
# @summary Verify the basic execution of the javadoc classes in classes.jar.

TESTSRC=${TESTSRC:-.}
TOPDIR=${TESTSRC}/../../..
TESTJAVAEXE="${TESTJAVA:+${TESTJAVA}/bin/}java"

rm -rf doc
mkdir doc

"${TESTJAVAEXE}" -Xbootclasspath/p:${TOPDIR}/dist/lib/classes.jar \
    com.sun.tools.javadoc.Main \
    -d doc "${TESTSRC}"/../HelloWorld.java

( cd doc ; find . -type f -print | LANG=C sort ) > javadoc.tmp

if diff ${TESTSRC}/../HelloWorld.javadoc.gold.txt javadoc.tmp ; then
    echo "Test passed."
else
    echo "Test failed."
    exit 1
fi
