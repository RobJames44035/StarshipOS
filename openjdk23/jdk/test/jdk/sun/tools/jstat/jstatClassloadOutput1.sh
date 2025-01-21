#
# StarshipOS Copyright (c) 2010-2025. R.A. James
#

# @test
# @bug 6959965
# @run shell jstatClassloadOutput1.sh
# @summary Test that output of 'jstat -classload 0' has expected line counts

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup
verify_os

JSTAT="${TESTJAVA}/bin/jstat"

${JSTAT} ${COMMON_JSTAT_FLAGS} -classload -J-Djstat.showUnsupported=true 0 2>&1 | awk -f ${TESTSRC}/classloadOutput1.awk
