#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825 6364329
# @run shell jstatHelp.sh
# @summary Test that output of 'jstat -?', 'jstat -h', 'jstat --help' and 'jstat' matches the usage.out file

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup

JSTAT="${TESTJAVA}/bin/jstat"

rm -f jstat.out 2>/dev/null
${JSTAT} -J-XX:+UsePerfData -? > jstat.out 2>&1

diff -w jstat.out ${TESTSRC}/usage.out
if [ $? != 0 ]
then
  echo "Output of jstat -? differs from expected output. Failed."
  exit 1
fi

rm -f jstat.out 2>/dev/null
${JSTAT} -J-XX:+UsePerfData --help > jstat.out 2>&1

diff -w jstat.out ${TESTSRC}/usage.out
if [ $? != 0 ]
then
  echo "Output of jstat -h differs from expected output. Failed."
  exit 1
fi

rm -f jstat.out 2>/dev/null
${JSTAT} -J-XX:+UsePerfData --help > jstat.out 2>&1

diff -w jstat.out ${TESTSRC}/usage.out
if [ $? != 0 ]
then
  echo "Output of jstat --help differs from expected output. Failed."
  exit 1
fi

rm -f jstat.out 2>/dev/null
${JSTAT} -J-XX:+UsePerfData > jstat.out 2>&1

diff -w jstat.out ${TESTSRC}/usage.out
if [ $? != 0 ]
then
  echo "Output of jstat differs from expected output. Failed."
  exit 1
fi

exit 0
