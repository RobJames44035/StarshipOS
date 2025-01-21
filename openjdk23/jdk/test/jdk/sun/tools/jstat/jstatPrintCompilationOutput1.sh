#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatPrintCompilationOutput1.sh
# @summary Test that output of 'jstat -printcompilation 0' has expected line counts

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup
verify_os

JSTAT="${TESTJAVA}/bin/jstat"

# run with -Xcomp as jstat may complete too quickly to assure
# that compilation occurs.
${JSTAT} ${COMMON_JSTAT_FLAGS} -J-Xcomp -printcompilation 0 2>&1 | awk -f ${TESTSRC}/printCompilationOutput1.awk
