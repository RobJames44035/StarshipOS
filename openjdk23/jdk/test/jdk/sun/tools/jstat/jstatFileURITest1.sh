#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 4990825
# @run shell jstatFileURITest1.sh
# @summary Test that output of 'jstat -gcutil file:path' has expected line counts

. ${TESTSRC-.}/../../jvmstat/testlibrary/utils.sh

setup

JSTAT="${TESTJAVA}/bin/jstat"
RC=0

OS=`uname -s`
case ${OS} in
Windows*)
    # work-around for strange problems trying to translate back slash
    # characters into forward slash characters in an effort to convert
    # TESTSRC into a canonical form useable as URI path.
    cp ${TESTSRC}/hsperfdata_3433 .
    ${JSTAT} ${COMMON_JSTAT_FLAGS} -gcutil file:/`pwd`/hsperfdata_3433 2>&1 | awk -f ${TESTSRC}/fileURITest1.awk
    RC=$?
    rm -f hsperfdata_3433 2>&1 > /dev/null
    ;;
*)
    ${JSTAT} ${COMMON_JSTAT_FLAGS} -gcutil file:${TESTSRC}/hsperfdata_3433 2>&1 | awk -f ${TESTSRC}/fileURITest1.awk
    RC=$?
    ;;
esac

echo ${RC}
