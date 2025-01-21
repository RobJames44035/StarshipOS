#!/bin/sh

#
# StarshipOS Copyright (c) 2025. R.A. James
#





# @test
# @bug 8058930 7077826
# @summary java.awt.GraphicsEnvironment.getHeadlessProperty() does not work for AIX
#
# @build TestDetectHeadless
# @run shell TestDetectHeadless.sh

OS=`uname -s`
case "$OS" in
    Windows* | CYGWIN* | Darwin)
        echo "Passed"; exit 0 ;;
    * ) unset DISPLAY ;;
esac

${TESTJAVA}/bin/java ${TESTVMOPTS} \
    -cp ${TESTCLASSES} TestDetectHeadless

if [ $? -ne 0 ]; then
	exit 1;
fi

DISPLAY=
export DISPLAY

${TESTJAVA}/bin/java ${TESTVMOPTS} \
    -cp ${TESTCLASSES} TestDetectHeadless

exit $?
