#
# StarshipOS Copyright (c) 2013-2025. R.A. James
#

# @test
# @bug 7022100
# @summary Method annotations are incorrectly set when redefining classes.
# @author Stefan Karlsson
#
# @run shell MakeJAR3.sh RedefineMethodWithAnnotationsAgent 'Can-Redefine-Classes: true'
# @run build RedefineMethodWithAnnotationsTarget RedefineMethodWithAnnotationsApp RedefineMethodWithAnnotationsAnnotations
# @run shell RedefineMethodWithAnnotations.sh
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

cp "${TESTSRC}"/RedefineMethodWithAnnotationsTarget_2.java \
    RedefineMethodWithAnnotationsTarget.java
cp "${TESTSRC}"/RedefineMethodWithAnnotationsAnnotations.java \
    RedefineMethodWithAnnotationsAnnotations.java

"${JAVAC}" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d . \
    RedefineMethodWithAnnotationsTarget.java \
    RedefineMethodWithAnnotationsAnnotations.java

"${JAVA}" ${TESTVMOPTS} ${TESTJAVAOPTS} -javaagent:RedefineMethodWithAnnotationsAgent.jar \
    -XX:+UnlockDiagnosticVMOptions -XX:+StressLdcRewrite -XX:+IgnoreUnrecognizedVMOptions \
    -cp "${TESTCLASSES}" RedefineMethodWithAnnotationsApp > output.log 2>&1
cat output.log

MESG="Exception|fatal"
egrep "$MESG" output.log
result=$?
if [ "$result" = 0 ]; then
    echo "FAIL: found '$MESG' in the test output"
    result=1
else
    echo "PASS: did NOT find '$MESG' in the test output"
    result=0
fi

exit $result
