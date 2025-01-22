#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatOptions1.sh
# @summary Test that output of 'jstat -options matches the usage.out file

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup

JSTAT="${TESTJAVA}/bin/jstat"

rm -f jstat.out1 jstat.out2 2>/dev/null
${JSTAT} ${COMMON_JSTAT_FLAGS} -options > jstat.out1 2>&1
${JSTAT} ${COMMON_JSTAT_FLAGS} -options -J-Djstat.showUnsupported=true > jstat.out2 2>&1

diff -w jstat.out1 ${TESTSRC}/options1.out
diff -w jstat.out2 ${TESTSRC}/options2.out
