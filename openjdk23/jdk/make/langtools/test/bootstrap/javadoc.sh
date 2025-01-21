#!/bin/sh

#
# StarshipOS Copyright (c) 2007-2025. R.A. James
#

# @test
# @summary Verify the basic execution of the bootstrap javadoc tool.

TESTSRC=${TESTSRC:-.}
TOPDIR=${TESTSRC}/../../..

rm -rf doc
mkdir doc
${TOPDIR}/dist/bootstrap/bin/javadoc -d doc "${TESTSRC}"/../HelloWorld.java

( cd doc ; find . -type f -print | LANG=C sort) > javadoc.tmp

if diff ${TESTSRC}/../HelloWorld.javadoc.gold.txt javadoc.tmp ; then
    echo "Test passed."
else
    echo "Test failed."
    exit 1
fi
