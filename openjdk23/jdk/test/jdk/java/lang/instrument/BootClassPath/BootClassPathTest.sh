#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 5055293 8273188
# @summary Test non US-ASCII characters in the value of the Boot-Class-Path
#          attribute.
#
# @key intermittent
# @run shell/timeout=240 BootClassPathTest.sh

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
JAR="${COMPILEJAVA}"/bin/jar

echo "Creating manifest file..."

"$JAVAC" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d "${TESTCLASSES}" "${TESTSRC}"/Setup.java

# java Setup <workdir> <premain-class>
# - outputs boot class path to boot.dir

OS=`uname -s`
case ${OS} in
    CYGWIN*)
        CYGWIN="CYGWIN"
        ;;
    *)
        CYGWIN=""
        ;;
esac

"$JAVA" ${TESTVMOPTS} ${TESTJAVAOPTS} -classpath "${TESTCLASSES}" Setup "${TESTCLASSES}" Agent "${CYGWIN}"

BOOTDIR=`cat ${TESTCLASSES}/boot.dir`

echo "Created ${BOOTDIR}"

echo "Building test classes..."

"$JAVAC" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d "${TESTCLASSES}" \
    "${TESTSRC}"/Agent.java "${TESTSRC}"/DummyMain.java
"$JAVAC" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d "${BOOTDIR}" \
    "${TESTSRC}"/AgentSupport.java

echo "Creating agent jar file..."

"$JAR" ${TESTTOOLVMOPTS} -cvfm "${TESTCLASSES}"/Agent.jar "${TESTCLASSES}"/MANIFEST.MF \
    -C "${TESTCLASSES}" Agent.class || exit 1

echo "Running test..."

"${JAVA}" ${TESTVMOPTS} ${TESTJAVAOPTS} -javaagent:"${TESTCLASSES}"/Agent.jar -classpath "${TESTCLASSES}" DummyMain
result=$?

echo "Cleanup..."

"$JAVAC" ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d "${TESTCLASSES}" \
    "${TESTSRC}"/Cleanup.java
"$JAVA" ${TESTVMOPTS} ${TESTJAVAOPTS} -classpath "${TESTCLASSES}" Cleanup "${BOOTDIR}"

exit $result
