#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatLineCounts3.sh
# @summary Test that output of 'jstat -gcutil -h 10 250 10' has expected line counts

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup
verify_os

JSTAT="${TESTJAVA}/bin/jstat"

${JSTAT} ${COMMON_JSTAT_FLAGS} -gcutil -h 10 0 250 10 2>&1 | awk -f ${TESTSRC}/lineCounts3.awk
${JSTAT} ${COMMON_JSTAT_FLAGS} -J-XX:+UseParallelGC -gcutil -h 10 0 250 10 2>&1 | awk -f ${TESTSRC}/lineCounts3.awk
