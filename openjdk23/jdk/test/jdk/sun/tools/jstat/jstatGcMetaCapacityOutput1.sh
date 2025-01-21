#
# StarshipOS Copyright (c) 2013-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatGcMetaCapacityOutput1.sh
# @summary Test that output of 'jstat -gcmetacapacity 0' has expected line counts

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup
verify_os

JSTAT="${TESTJAVA}/bin/jstat"

${JSTAT} ${COMMON_JSTAT_FLAGS} -gcmetacapacity 0 2>&1 | awk -f ${TESTSRC}/gcMetaCapacityOutput1.awk
${JSTAT} ${COMMON_JSTAT_FLAGS} -J-XX:+UseParallelGC -gcmetacapacity 0 2>&1 | awk -f ${TESTSRC}/gcMetaCapacityOutput1.awk
