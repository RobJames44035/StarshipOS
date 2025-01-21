#!/bin/sh

#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#


# @test
# @bug 6265810
# @run shell jrunscript-helpTest.sh
# @summary Test that output of 'jrunscript -?' is not empty

. ${TESTSRC-.}/common.sh

setup

rm -f jrunscript-helpTest.out 2>/dev/null
${JRUNSCRIPT} -? > jrunscript-helpTest.out 2>&1

if [ ! -s jrunscript-helpTest.out ]
then
  echo "Output of jrunscript -? is empty. Failed."
  rm -f jrunscript-helpTest.out 2>/dev/null
  exit 1
fi

rm -f jrunscript-helpTest.out 2>/dev/null
${JRUNSCRIPT} -help > jrunscript-helpTest.out 2>&1

if [ ! -s jrunscript-helpTest.out ]
then
  echo "Output of jrunscript -help is empty. Failed."
  rm -f jrunscript-helpTest.out 2>/dev/null
  exit 1
fi

rm -f jrunscript-helpTest.out
echo "Passed"
exit 0
