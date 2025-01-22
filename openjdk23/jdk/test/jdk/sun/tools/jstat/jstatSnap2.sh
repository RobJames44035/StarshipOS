#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatSnap2.sh
# @summary Test that output of 'jstat -J-Djstat.showUnsupported -snap' matches a specific pattern

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup
verify_os

JSTAT="${TESTJAVA}/bin/jstat"

${JSTAT} ${COMMON_JSTAT_FLAGS} -J-Djstat.showUnsupported=true -snap 0 2>&1 | awk -f ${TESTSRC}/snap2.awk
