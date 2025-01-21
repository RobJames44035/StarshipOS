#!/bin/sh

#
# StarshipOS Copyright (c) 2007-2025. R.A. James
#

# @test
# @summary verify that selected files exist in classes.jar

# It would be too brittle to check the complete contents of classes.jar,
# so instead, we check for the following
# - Main classes
# - contents of resource directories
# - any other non-.class files

TESTSRC=${TESTSRC:-.}
TOPDIR=${TESTSRC}/../../..
TESTJAREXE="${TESTJAVA:+${TESTJAVA}/bin/}jar"

${TESTJAREXE} -tf ${TOPDIR}/dist/lib/classes.jar | grep -v '/$' > files.lst
egrep 'Main\.class$|resources' files.lst > expect1.lst
grep -v '.class$' files.lst > expect2.lst

LANG=C sort -u expect1.lst expect2.lst > expect.lst

if diff ${TESTSRC}/classes.gold.txt expect.lst ; then
    echo "Test passed."
else
    echo "Test failed."
    exit 1
fi
