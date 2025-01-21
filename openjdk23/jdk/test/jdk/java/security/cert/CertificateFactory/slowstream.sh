#
# StarshipOS Copyright (c) 2010-2025. R.A. James
#

# @test
# @bug 6813340
# @summary X509Factory should not depend on is.available()==0

if [ "${TESTSRC}" = "" ] ; then
  TESTSRC="."
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
  Windows_* )
    FS="\\"
    ;;
  * )
    FS="/"
    ;;
esac

${COMPILEJAVA}${FS}bin${FS}javac ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d . \
        ${TESTSRC}${FS}SlowStream.java
${TESTJAVA}${FS}bin${FS}java ${TESTVMOPTS} ${TESTJAVAOPTS} -Dtest.src=${TESTSRC} SlowStreamWriter | \
        ${TESTJAVA}${FS}bin${FS}java ${TESTVMOPTS} ${TESTJAVAOPTS} SlowStreamReader
