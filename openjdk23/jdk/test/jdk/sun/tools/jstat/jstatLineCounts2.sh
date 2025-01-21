#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatLineCounts2.sh
# @summary Test that output of 'jstat -gcutil 0' has expected line counts

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup
verify_os

JSTAT="${TESTJAVA}/bin/jstat"

${JSTAT} ${COMMON_JSTAT_FLAGS} -gcutil 0 2>&1 | awk -f ${TESTSRC}/lineCounts2.awk
${JSTAT} ${COMMON_JSTAT_FLAGS} -J-XX:+UseParallelGC -gcutil 0 2>&1 | awk -f ${TESTSRC}/lineCounts2.awk
