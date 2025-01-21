#!/bin/sh

#
# StarshipOS Copyright (c) 2012-2025. R.A. James
#

# @test JAWT.sh
# @key headful
# @bug 7190587
# @summary Tests Java AWT native interface library
# @author kshefov
# @run shell JAWT.sh

# NB: To run on Windows with MKS and Visual Studio compiler
# add the following options to jtreg: -e INCLUDE="%INCLUDE%;." -e LIB="%LIB%;."

if [ "${TESTSRC}" = "" ]
then TESTSRC=.
fi

if [ "${TESTJAVA}" = "" ]
then
  PARENT=`dirname \`which java\``
  TESTJAVA=`dirname ${PARENT}`
  echo "TESTJAVA not set, selecting " ${TESTJAVA}
  echo "If this is incorrect, try setting the variable manually."
fi

# set platform-dependent variables
OS=`uname -s`
case "$OS" in
  Linux )
    NULL=/dev/null
    PS=":"
    FS="/"
    ${TESTJAVA}${FS}bin${FS}java -version 2>&1 | grep '64-Bit' > $NULL
    if [ $? -eq '0' ]
    then
        ARCH="amd64"
    else
        ARCH="i386"
    fi
    SYST="linux"
    MAKEFILE="Makefile.unix"
    CC="gcc"
	MAKE="make"
	LD_LIBRARY_PATH="."
    ;;
  AIX )
      echo "Test passed. Not supported on AIX."
      exit 0
    ;;
  Windows* )
    NULL=null
    PS=";"
    FS="\\"
    MAKEFILE="Makefile.win"
    CC="cl"
	MAKE="nmake"
	${TESTJAVA}${FS}bin${FS}java -version 2>&1 | grep '64-Bit' > $NULL
    if [ "$?" -eq '0' ]
    then
        ARCH="amd64"
    else
        ARCH="i386"
    fi
	SYST="windows"
    ;;
  CYGWIN* )
    NULL=/dev/null
    PS=":"
    FS="/"
    MAKEFILE="Makefile.cygwin"
    CC="gcc"
	${TESTJAVA}${FS}bin${FS}java -version 2>&1 | grep '64-Bit' > $NULL
    if [ "$?" -eq '0' ]
    then
        ARCH="amd64"
    else
        ARCH="i386"
    fi
	SYST="cygwin"
	MAKE="make"
    ;;
  Darwin )
    echo "Test passed. This test is not for MacOS."
    exit 0;
    ;;
  * )
    echo "Unrecognized system!"
    exit 1;
    ;;
esac

# Skip unsupported platforms
case `uname -m` in
    arm* | ppc* | s390* )
      echo "Test passed. Not supported on current architecture."
      exit 0
      ;;
esac

echo "OS-ARCH is" ${SYST}-${ARCH}
${TESTJAVA}${FS}bin${FS}java -fullversion 2>&1

which ${MAKE} >${NULL} 2>&1
if [ "$?" -ne '0' ]
then
    echo "No make found. Test passed."
    exit 0
fi

which ${CC} >${NULL} 2>&1
if [ "$?" -ne '0' ]
then
    echo "No C compiler found. Test passed."
    exit 0
fi

cp ${TESTSRC}${FS}${MAKEFILE} .

JAVA=${TESTJAVA}${FS}bin${FS}java
JAVAC=${TESTJAVA}${FS}bin${FS}javac

export CC SYST ARCH LD_LIBRARY_PATH

${JAVAC} -d . -h . ${TESTSRC}${FS}MyCanvas.java
${MAKE} -f ${MAKEFILE}
${JAVA} ${TESTVMOPTS} -classpath . MyCanvas

exit $?

