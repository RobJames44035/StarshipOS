#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#

# @test
# @bug 6173575
# @summary Unit tests for appendToBootstrapClassLoaderSearch and
#   appendToSystemClasLoaderSearch methods.
#
# @run shell/timeout=240 CircularityErrorTest.sh

if [ "${TESTSRC}" = "" ]
then
  echo "TESTSRC not set.  Test cannot execute.  Failed."
  exit 1
fi

if [ "${COMPILEJAVA}" = "" ]
then
  COMPILEJAVA="${TESTJAVA}"
fi

. ${TESTSRC}/CommonSetup.sh

# Setup to create circularity condition

# B extends A
# This yields us A.jar (containing A.class) and B.keep (class file)
rm -f "${TESTCLASSES}"/A.java "${TESTCLASSES}"/B.java
cp "${TESTSRC}"/A.1 "${TESTCLASSES}"/A.java
cp "${TESTSRC}"/B.1 "${TESTCLASSES}"/B.java
(cd "${TESTCLASSES}"; \
    $JAVAC ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} A.java B.java; \
    $JAVAC ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} -d . "${TESTSRC}"/CircularityErrorTest.java; \
    $JAR ${TESTTOOLVMOPTS} cf A.jar A.class; \
    rm -f A.class; mv B.class B.keep)

# A extends B
# This yields us A.class
rm -f "${TESTCLASSES}"/A.java "${TESTCLASSES}"/B.java
cp "${TESTSRC}"/A.2 "${TESTCLASSES}"/A.java
cp "${TESTSRC}"/B.2 "${TESTCLASSES}"/B.java
(cd "${TESTCLASSES}"; \
     $JAVAC ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} A.java B.java; rm -f B.class A.java B.java)

# Move B.keep to B.class creates the A extends B and
# B extends A condition.
(cd "${TESTCLASSES}"; mv B.keep B.class)

# Create the manifest
MANIFEST="${TESTCLASSES}"/agent.mf
rm -f "${MANIFEST}"
echo "Premain-Class: CircularityErrorTest" > "${MANIFEST}"

# Setup test case as an agent
$JAR ${TESTTOOLVMOPTS} -cfm "${TESTCLASSES}"/CircularityErrorTest.jar "${MANIFEST}" \
  -C "${TESTCLASSES}" CircularityErrorTest.class

# Finally we run the test
(cd "${TESTCLASSES}";
  $JAVA ${TESTVMOPTS} ${TESTJAVAOPTS} -javaagent:CircularityErrorTest.jar CircularityErrorTest)
