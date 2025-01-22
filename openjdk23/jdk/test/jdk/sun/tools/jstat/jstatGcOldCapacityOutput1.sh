#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatGcOldCapacityOutput1.sh
# @summary Test that output of 'jstat -gcoldcapcaity 0' has expected line counts

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup
verify_os

JSTAT="${TESTJAVA}/bin/jstat"

${JSTAT} ${COMMON_JSTAT_FLAGS} -gcoldcapacity 0 2>&1 | awk -f ${TESTSRC}/gcOldCapacityOutput1.awk
${JSTAT} ${COMMON_JSTAT_FLAGS} -J-XX:+UseParallelGC -gcoldcapacity 0 2>&1 | awk -f ${TESTSRC}/gcOldCapacityOutput1.awk
