#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatTimeStamp1.sh
# @summary Test that output of 'jstat -gcutil -t 0' has expected format

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup
verify_os

JSTAT="${TESTJAVA}/bin/jstat"

${JSTAT} ${COMMON_JSTAT_FLAGS} -gcutil -t 0 2>&1 | awk -f ${TESTSRC}/timeStamp1.awk
${JSTAT} ${COMMON_JSTAT_FLAGS} -J-XX:+UseParallelGC -gcutil -t 0 2>&1 | awk -f ${TESTSRC}/timeStamp1.awk
