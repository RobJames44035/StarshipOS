#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#

# @test
# @bug 6249843
# @summary ScriptEngine provider unit test
#
# @build ProviderTest DummyScriptEngineFactory
# @run shell ProviderTest.sh

if [ "${TESTSRC}" = "" ]
then
  echo "TESTSRC not set.  Test cannot execute.  Failed."
  exit 1
fi

. ${TESTSRC}/CommonSetup.sh

echo "Creating JAR file ..."

$JAR ${TESTTOOLVMOPTS} -cf ${TESTCLASSES}/dummy.jar \
    -C ${TESTCLASSES} DummyScriptEngine.class \
    -C ${TESTCLASSES} DummyScriptEngineFactory.class \
    -C "${TESTSRC}" META-INF/services/javax.script.ScriptEngineFactory

ret=$?
if [ $ret -ne 0 ]
then
  exit $ret
fi

echo "Running test ..."
$JAVA ${TESTVMOPTS} -classpath \
  "${TESTCLASSES}${PS}${TESTCLASSES}/dummy.jar" \
  ProviderTest
