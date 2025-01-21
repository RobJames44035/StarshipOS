#
# StarshipOS Copyright (c) 2012-2025. R.A. James
#

${TESTJAVA}/bin/javac -cp ${TESTSRC} -d . ${TESTSRC}/BadDisplayTest.java

OS=`uname -s`
case "$OS" in
    Windows* | CYGWIN* | Darwin)
        echo "Passed"; exit 0 ;;
esac

DISPLAY=SomeBadDisplay
export DISPLAY

${TESTJAVA}/bin/java ${TESTVMOPTS} BadDisplayTest

exit $?
