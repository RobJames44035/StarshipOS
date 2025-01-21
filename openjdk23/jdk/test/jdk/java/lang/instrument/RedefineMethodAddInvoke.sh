#
# StarshipOS Copyright (c) 2008-2025. R.A. James
#

# @test
# @bug 6667089
# @summary Reflexive invocation of newly added methods broken.
# @author Daniel D. Daugherty
#
# @run shell MakeJAR3.sh RedefineMethodAddInvokeAgent 'Can-Redefine-Classes: true'
# @run build RedefineMethodAddInvokeApp
# @run shell RedefineMethodAddInvoke.sh
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

cp "${TESTSRC}"/RedefineMethodAddInvokeTarget_1.java \
    RedefineMethodAddInvokeTarget.java
"${JAVAC}" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d . RedefineMethodAddInvokeTarget.java
mv RedefineMethodAddInvokeTarget.java RedefineMethodAddInvokeTarget_1.java
mv RedefineMethodAddInvokeTarget.class RedefineMethodAddInvokeTarget_1.class

cp "${TESTSRC}"/RedefineMethodAddInvokeTarget_2.java \
    RedefineMethodAddInvokeTarget.java
"${JAVAC}" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d . RedefineMethodAddInvokeTarget.java
mv RedefineMethodAddInvokeTarget.java RedefineMethodAddInvokeTarget_2.java
mv RedefineMethodAddInvokeTarget.class RedefineMethodAddInvokeTarget_2.class

"${JAVA}" ${TESTVMOPTS} ${TESTJAVAOPTS} -javaagent:RedefineMethodAddInvokeAgent.jar \
    -XX:+AllowRedefinitionToAddDeleteMethods \
    -classpath "${TESTCLASSES}" RedefineMethodAddInvokeApp > output.log 2>&1
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
