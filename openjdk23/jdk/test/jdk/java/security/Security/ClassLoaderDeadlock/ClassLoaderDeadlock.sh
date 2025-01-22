#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 5094825
# @summary verify no deadlock if crypto provider in other classloader is used to verify signed jars
#
# @run shell/timeout=30 ClassLoaderDeadlock.sh

# set a few environment variables so that the shell-script can run stand-alone
# in the source directory
if [ "${TESTSRC}" = "" ] ; then
   TESTSRC="."
fi

if [ "${TESTCLASSES}" = "" ] ; then
   TESTCLASSES="."
fi

if [ "${TESTJAVA}" = "" ] ; then
   echo "TESTJAVA not set.  Test cannot execute."
   echo "FAILED!!!"
   exit 1
fi

if [ "${COMPILEJAVA}" = "" ]; then
   COMPILEJAVA="${TESTJAVA}"
fi

# set platform-dependent variables
OS=`uname -s`
case "$OS" in
  Linux )
    PATHSEP=":"
    FILESEP="/"
    ;;
  Darwin )
    PATHSEP=":"
    FILESEP="/"
    ;;
  AIX )
    PATHSEP=":"
    FILESEP="/"
    ;;
  CYGWIN* )
    PATHSEP=";"
    FILESEP="/"
    ;;
  Windows* )
    PATHSEP=";"
    FILESEP="\\"
    ;;
  * )
    echo "Unrecognized system!"
    exit 1;
    ;;
esac

cd ${TESTCLASSES}${FILESEP}
if [ ! -d provider ] ; then
    mkdir provider
fi

# compile the test program
${COMPILEJAVA}${FILESEP}bin${FILESEP}javac ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} \
        -d ${TESTCLASSES}${FILESEP} \
        ${TESTSRC}${FILESEP}ClassLoaderDeadlock.java

${COMPILEJAVA}${FILESEP}bin${FILESEP}javac ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} \
        -d ${TESTCLASSES}${FILESEP}provider${FILESEP} \
        ${TESTSRC}${FILESEP}provider${FILESEP}HashProvider.java

# run the test
${TESTJAVA}${FILESEP}bin${FILESEP}java ${TESTVMOPTS} ${TESTJAVAOPTS} \
        -classpath "${TESTCLASSES}${PATHSEP}${TESTSRC}${FILESEP}Deadlock.jar" \
	-Djava.awt.headless=true \
        ClassLoaderDeadlock

STATUS=$?

# clean up
rm -f 'ClassLoaderDeadlock.class' 'ClassLoaderDeadlock$1.class' \
'ClassLoaderDeadlock$DelayClassLoader.class' \
provider${FILESEP}HashProvider.class

exit $STATUS
