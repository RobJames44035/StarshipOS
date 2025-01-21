#
# StarshipOS Copyright (c) 2011-2025. R.A. James
#

# @test
# @bug 7121600 8016838
# @summary Redefine a big class.
# @author Daniel D. Daugherty
#
# @key intermittent
# @modules java.instrument
#          java.management
# @run shell MakeJAR3.sh RedefineBigClassAgent 'Can-Redefine-Classes: true'
# @run build BigClass RedefineBigClassApp NMTHelper
# @run shell/timeout=600 RedefineBigClass.sh
#

if [ "${TESTJAVA}" = "" ]
then
  echo "TESTJAVA not set.  Test cannot execute.  Failed."
  exit 1
fi

if [ "${COMPILEJAVA}" = "" ]
then
  COMPILEJAVA="${TESTJAVA}"
fi
echo "COMPILEJAVA=${COMPILEJAVA}"

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

JAVAC="${COMPILEJAVA}"/bin/javac
JAVA="${TESTJAVA}"/bin/java

# Does this VM support the 'detail' level of NMT?
"${JAVA}" ${TESTVMOPTS} ${TESTJAVAOPTS} -XX:NativeMemoryTracking=detail -version
if [ "$?" = 0 ]; then
    NMT=-XX:NativeMemoryTracking=detail
else
    NMT=-XX:NativeMemoryTracking=summary
fi

"${JAVA}" ${TESTVMOPTS} ${TESTJAVAOPTS} \
    -Xlog:redefine+class+load=debug,redefine+class+load+exceptions=info ${NMT} \
    -javaagent:RedefineBigClassAgent.jar=BigClass.class \
    -classpath "${TESTCLASSES}" RedefineBigClassApp \
    > output.log 2>&1
result=$?

cat output.log

if [ "$result" = 0 ]; then
    echo "PASS: RedefineBigClassApp exited with status of 0."
else
    echo "FAIL: RedefineBigClassApp exited with status of $result"
    exit "$result"
fi

MESG="Exception"
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
