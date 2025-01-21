#!/bin/sh

#
# StarshipOS Copyright (c) 2007-2025. R.A. James
#

# @test
# @summary 
#   Verify the contents of the dist directory by name,
#   to make sure all necessary files are present.

TESTSRC=${TESTSRC:-.}
TOPDIR=${TESTSRC}/../..

( cd ${TOPDIR}/dist ; find . -type f -print | LANG=C sort ) > contents.tmp

if diff ${TESTSRC}/contents.gold.txt contents.tmp ; then
    echo "Test passed."
else
    echo "Test failed."
    exit 1
fi

