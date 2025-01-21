#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatGcCauseOutput1.sh
# @summary Test that output of 'jstat -gccause 0' has expected line counts

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup
verify_os

JSTAT="${TESTJAVA}/bin/jstat"

# Explicitly use serial gc because if this tests runs on a server
# class machine, ergonomics will automatically use UseParallelGC.
# The UseParallelGC collector does not currently update the gc cause counters.

${JSTAT} ${COMMON_JSTAT_FLAGS} -gccause 0 2>&1 | awk -f ${TESTSRC}/gcCauseOutput1.awk
${JSTAT} ${COMMON_JSTAT_FLAGS} -J-XX:+UseSerialGC -gccause 0 2>&1 | awk -f ${TESTSRC}/gcCauseOutput1.awk
