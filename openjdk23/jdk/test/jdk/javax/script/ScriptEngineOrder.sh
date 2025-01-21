#
# StarshipOS Copyright (c) 2018-2025. R.A. James
#

# @test
# @bug 8011697
# @summary test to check consistency in discovering and returning script engine 
# by ScriptEngineManager
#
# @run shell ScriptEngineOrder.sh

set -x
if [ "${TESTSRC}" = "" ]
then
  echo "TESTSRC not set.  Test cannot execute.  Failed."
  exit 1
fi

. ${TESTSRC}/CommonSetup.sh

echo "Building dummy script engine modules.."
#test to check the consistency in returning engines by ScriptEngineManager
$JAVAC --limit-modules java.base,java.logging,java.scripting,jdk.scripting.dummyNashorn,jdk.scripting.dummyRhino,jdk.scripting.testEngines -d ${TESTCLASSES}/mods --module-source-path ${TESTSRC}/multiEngines $(find ${TESTSRC}/multiEngines -name *.java)

echo "Running script engine test.."
$JAVA --limit-modules java.base,java.logging,java.scripting,jdk.scripting.dummyNashorn,jdk.scripting.dummyRhino,jdk.scripting.testEngines --module-path ${TESTCLASSES}/mods --module jdk.scripting.testEngines/jdk.test.engines.ScriptEngineTest

ret=$?
if [ $ret -ne 0 ]
then
  exit $ret
fi

