#!/bin/sh
#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#

# @test
# @key headful
# @bug 6363434 6588884
# @summary Verify that shared memory pixmaps are not broken
# by filling a VolatileImage with red color and copying it
# to the screen.
# Note that we force the use of shared memory pixmaps.
# @author Dmitri.Trembovetski

echo "TESTJAVA=${TESTJAVA}"
echo "TESTSRC=${TESTSRC}"
echo "TESTCLASSES=${TESTCLASSES}"
cd ${TESTSRC}
${TESTJAVA}/bin/javac -d ${TESTCLASSES} SharedMemoryPixmapsTest.java
cd ${TESTCLASSES}

J2D_PIXMAPS=shared
export J2D_PIXMAPS

${TESTJAVA}/bin/java ${TESTVMOPTS} SharedMemoryPixmapsTest

if [ $? -ne 0 ]; then
  echo "Test failed!"
  exit 1
fi

exit 0
