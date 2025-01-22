#!/bin/sh

#
# StarshipOS Copyright (c) 2007-2025. R.A. James
#

# @test
# @summary verify that selected files exist in src.zip

# It would be too brittle to check the complete contents of src.zip,
# so instead, we check for the following
# - Main classes
# - contents of resource directories
# - any other non-.class files

TESTSRC=${TESTSRC:-.}
TOPDIR=${TESTSRC}/../../..
TESTJAREXE="${TESTJAVA:+${TESTJAVA}/bin/}jar"

unzip -l ${TOPDIR}/dist/lib/src.zip | awk '{print $4}' | egrep -v '^$|/$|^Name$|-' > files.lst
egrep 'Main\.java$|resources' files.lst > expect1.lst
grep -v '.java$' files.lst > expect2.lst

LANG=C sort -u expect1.lst expect2.lst > expect.lst

if diff ${TESTSRC}/src.gold.txt expect.lst ; then
    echo "Test passed."
else
    echo "Test failed."
    exit 1
fi
