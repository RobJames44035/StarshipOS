#
# StarshipOS Copyright (c) 2008-2025. R.A. James
#

# @test
# @bug 6572160
# @summary stress getObjectSize() API
# @author Daniel D. Daugherty as modified from the code of fischman@google.com
#
# @run build StressGetObjectSizeApp
# @run shell MakeJAR.sh basicAgent
# @run shell StressGetObjectSizeTest.sh
#

if [ "${TESTJAVA}" = "" ]
then
  echo "TESTJAVA not set.  Test cannot execute.  Failed."
  exit 1
fi

if [ "${TESTSRC}" = "" ]
then
  echo "TESTSRC not set.  Test cannot execute.  Failed."
  exit 1
fi

if [ "${TESTCLASSES}" = "" ]
then
  echo "TESTCLASSES not set.  Test cannot execute.  Failed."
  exit 1
fi

JAVA="${TESTJAVA}"/bin/java

"${JAVA}" ${TESTVMOPTS} ${TESTJAVAOPTS} -javaagent:basicAgent.jar \
    -classpath "${TESTCLASSES}" StressGetObjectSizeApp StressGetObjectSizeApp \
    > output.log 2>&1
cat output.log

MESG="ASSERTION FAILED"
grep "$MESG" output.log
result=$?
if [ "$result" = 0 ]; then
    echo "FAIL: found '$MESG' in the test output"
    result=1
else
    echo "PASS: did NOT find '$MESG' in the test output"
    result=0
fi

exit $result
