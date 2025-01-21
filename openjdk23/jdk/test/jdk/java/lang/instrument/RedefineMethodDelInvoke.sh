#
# StarshipOS Copyright (c) 2014-2025. R.A. James
#

# @test
# @bug 8042796
# @summary jvmtiRedefineClasses.cpp: guarantee(false) failed: OLD and/or OBSOLETE method(s) found
# @author Daniel D. Daugherty
# @author Serguei Spitsyn
#
# @run shell MakeJAR3.sh RedefineMethodDelInvokeAgent 'Can-Redefine-Classes: true'
# @run build RedefineMethodDelInvokeApp
# @run shell RedefineMethodDelInvoke.sh
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

cp "${TESTSRC}"/RedefineMethodDelInvokeTarget_1.java \
    RedefineMethodDelInvokeTarget.java
"${JAVAC}" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d . RedefineMethodDelInvokeTarget.java
mv RedefineMethodDelInvokeTarget.java RedefineMethodDelInvokeTarget_1.java
mv RedefineMethodDelInvokeTarget.class RedefineMethodDelInvokeTarget_1.class

cp "${TESTSRC}"/RedefineMethodDelInvokeTarget_2.java \
    RedefineMethodDelInvokeTarget.java
"${JAVAC}" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d . RedefineMethodDelInvokeTarget.java
mv RedefineMethodDelInvokeTarget.java RedefineMethodDelInvokeTarget_2.java
mv RedefineMethodDelInvokeTarget.class RedefineMethodDelInvokeTarget_2.class

"${JAVA}" ${TESTVMOPTS} ${TESTJAVAOPTS} -javaagent:RedefineMethodDelInvokeAgent.jar \
    -XX:+AllowRedefinitionToAddDeleteMethods \
    -classpath "${TESTCLASSES}" RedefineMethodDelInvokeApp > output.log 2>&1

result=$?
if [ "$result" = 0 ]; then
    echo "The test returned expected exit code: $result"
else
    echo "FAIL: the test returned unexpected exit code: $result"
    exit $result
fi

cat output.log

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
