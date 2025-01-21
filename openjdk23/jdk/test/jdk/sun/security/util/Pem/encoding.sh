#
# StarshipOS Copyright (c) 2016-2025. R.A. James
#

# @test
# @bug 8158633
# @summary BASE64 encoded cert not correctly parsed with UTF-16
# @build PemEncoding
# @run shell encoding.sh

# jtreg does not like -Dfile.encoding=UTF-16 inside a @run main line,
# therefore a shell test is written.

$TESTJAVA/bin/java $TESTVMOPTS $TESTJAVAOPTS -cp $TESTCLASSES \
        -Dfile.encoding=UTF-16 \
        PemEncoding $TESTSRC/../HostnameChecker/cert5.crt
