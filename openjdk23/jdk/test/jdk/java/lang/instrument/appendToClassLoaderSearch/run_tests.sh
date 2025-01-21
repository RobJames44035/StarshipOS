#!/bin/sh

#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#


# @test
# @bug 6173575 6388987
# @summary Unit tests for appendToBootstrapClassLoaderSearch and
#   appendToSystemClasLoaderSearch methods.
#
# @build Agent AgentSupport BootSupport BasicTest PrematureLoadTest DynamicTest
# @run shell/timeout=240 run_tests.sh

if [ "${TESTSRC}" = "" ]
then
  echo "TESTSRC not set.  Test cannot execute.  Failed."
  exit 1
fi

. ${TESTSRC}/CommonSetup.sh


# Simple tests

echo "Creating jar files for simple tests..."

cd ${TESTCLASSES}

"$JAR" ${TESTTOOLVMOPTS} -cfm Agent.jar "${TESTSRC}"/manifest.mf Agent.class
"$JAR" ${TESTTOOLVMOPTS} -cf  AgentSupport.jar AgentSupport.class
"$JAR" ${TESTTOOLVMOPTS} -cf  BootSupport.jar BootSupport.class
"$JAR" ${TESTTOOLVMOPTS} -cf  SimpleTests.jar BasicTest.class PrematureLoadTest.class

failures=0

go() {
    echo ''
    sh -xc "$JAVA ${TESTVMOPTS} ${TESTJAVAOPTS} -javaagent:Agent.jar -classpath SimpleTests.jar  $1 $2 $3" 2>&1
    if [ $? != 0 ]; then failures=`expr $failures + 1`; fi
}

go BasicTest
go PrematureLoadTest

# Functional tests

echo ''
echo "Setup for functional tests..."

# Create org.tools.Tracer in temp directory so that it's not seen on the
# system class path

mkdir tmp
"${JAVAC}" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d tmp "${TESTSRC}"/Tracer.java
(cd tmp; "${JAR}" ${TESTTOOLVMOPTS} cf ../Tracer.jar org/tools/Tracer.class)

# InstrumentedApplication is Application+instrmentation - don't copy as
# we don't want the original file permission

cat "${TESTSRC}"/InstrumentedApplication.java > ./Application.java
"${JAVAC}" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -classpath Tracer.jar -d . Application.java
mv Application.class InstrumentedApplication.bytes

cp "${TESTSRC}"/Application.java .
"${JAVAC}" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d . Application.java

sh -xc "$JAVA ${TESTVMOPTS} ${TESTJAVAOPTS} -classpath . -javaagent:Agent.jar DynamicTest" 2>&1
if [ $? != 0 ]; then failures=`expr $failures + 1`; fi

#
# Results
#
echo ''
if [ $failures -gt 0 ];
  then echo "$failures test(s) failed";
  else echo "All test(s) passed"; fi
exit $failures
