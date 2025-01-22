#
# StarshipOS Copyright (c) 2021-2025. R.A. James
#

# @test
# @bug 8240256 8269034
# @summary
# @library /test/lib/
# @build jdk.test.lib.util.ForceGC
#        jdk.test.lib.Platform
#        jdk.test.lib.Utils
# @run shell MultipleLogins.sh

# set a few environment variables so that the shell-script can run stand-alone
# in the source directory

# if running by hand on windows, change TESTSRC and TESTCLASSES to "."
if [ "${TESTSRC}" = "" ] ; then
    TESTSRC=`pwd`
fi
if [ "${TESTCLASSES}" = "" ] ; then
    TESTCLASSES=`pwd`
fi

if [ "${TESTCLASSPATH}" = "" ] ; then
    TESTCLASSPATH=`pwd`
fi

if [ "${COMPILEJAVA}" = "" ]; then
    COMPILEJAVA="${TESTJAVA}"
fi
echo TESTSRC=${TESTSRC}
echo TESTCLASSES=${TESTCLASSES}
echo TESTJAVA=${TESTJAVA}
echo COMPILEJAVA=${COMPILEJAVA}
echo ""

# let java test exit if platform unsupported

OS=`uname -s`
case "$OS" in
  Linux )
    FS="/"
    PS=":"
    CP="${FS}bin${FS}cp"
    CHMOD="${FS}bin${FS}chmod"
    ;;
  Darwin )
    FS="/"
    PS=":"
    CP="${FS}bin${FS}cp"
    CHMOD="${FS}bin${FS}chmod"
    ;;
  AIX )
    FS="/"
    PS=":"
    CP="${FS}bin${FS}cp"
    CHMOD="${FS}bin${FS}chmod"
    ;;
  Windows* )
    FS="\\"
    PS=";"
    CP="cp"
    CHMOD="chmod"
    ;;
  CYGWIN* )
    FS="/"
    PS=";"
    CP="cp"
    CHMOD="chmod"
    #
    # javac does not like /cygdrive produced by `pwd`
    #
    TESTSRC=`cygpath -d ${TESTSRC}`
    ;;
  * )
    echo "Unrecognized system!"
    exit 1;
    ;;
esac

# first make cert/key DBs writable

${CP} ${TESTSRC}${FS}..${FS}nss${FS}db${FS}cert9.db ${TESTCLASSES}
${CHMOD} +w ${TESTCLASSES}${FS}cert9.db

${CP} ${TESTSRC}${FS}..${FS}nss${FS}db${FS}key4.db ${TESTCLASSES}
${CHMOD} +w ${TESTCLASSES}${FS}key4.db

${CP} ${TESTSRC}${FS}..${FS}nss${FS}db${FS}cert8.db ${TESTCLASSES}
${CHMOD} +w ${TESTCLASSES}${FS}cert8.db

${CP} ${TESTSRC}${FS}..${FS}nss${FS}db${FS}key3.db ${TESTCLASSES}
${CHMOD} +w ${TESTCLASSES}${FS}key3.db

# compile test
${COMPILEJAVA}${FS}bin${FS}javac ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} \
        -classpath ${TESTCLASSPATH} \
        -d ${TESTCLASSES} \
        --add-modules jdk.crypto.cryptoki \
        --add-exports jdk.crypto.cryptoki/sun.security.pkcs11=ALL-UNNAMED \
        ${TESTSRC}${FS}..${FS}..${FS}..${FS}..${FS}..${FS}lib${FS}jdk${FS}test${FS}lib${FS}artifacts${FS}*.java \
        ${TESTSRC}${FS}..${FS}..${FS}..${FS}..${FS}..${FS}lib${FS}jtreg${FS}*.java \
        ${TESTSRC}${FS}MultipleLogins.java \
        ${TESTSRC}${FS}..${FS}PKCS11Test.java

TEST_ARGS="${TESTVMOPTS} ${TESTJAVAOPTS} -classpath ${TESTCLASSPATH} \
        --add-modules jdk.crypto.cryptoki \
        --add-exports jdk.crypto.cryptoki/sun.security.pkcs11=ALL-UNNAMED \
        -DCUSTOM_DB_DIR=${TESTCLASSES} \
        -DCUSTOM_P11_CONFIG=${TESTSRC}${FS}MultipleLogins-nss.txt \
        -Dtest.src=${TESTSRC} \
        -Dtest.classes=${TESTCLASSES} \
        -Djava.security.debug=${DEBUG}"

# run test without security manager
${TESTJAVA}${FS}bin${FS}java ${TEST_ARGS} MultipleLogins || exit 10

echo Done
exit 0
