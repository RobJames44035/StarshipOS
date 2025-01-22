#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatCompilerOutput1.sh
# @summary Test that output of 'jstat -compiler 0' has expected line counts

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup
verify_os

JSTAT="${TESTJAVA}/bin/jstat"

${JSTAT} ${COMMON_JSTAT_FLAGS} -compiler 0 2>&1 | awk -f ${TESTSRC}/compilerOutput1.awk
