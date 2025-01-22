#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#

# @test
# @bug 6173575
# @summary Unit tests for appendToBootstrapClassLoaderSearch and
#   appendToSystemClasLoaderSearch methods.
#
# @build ClassUnloadTest
# @run shell ClassUnloadTest.sh

if [ "${TESTSRC}" = "" ]
then
  echo "TESTSRC not set.  Test cannot execute.  Failed."
  exit 1
fi

. ${TESTSRC}/CommonSetup.sh

# Create Foo and Bar
# Foo has a reference to Bar but we deleted Bar so that
# a NoClassDefFoundError will be thrown when Foo tries to
# resolve the reference to Bar

OTHERDIR="${TESTCLASSES}"/other
mkdir "${OTHERDIR}"

FOO="${OTHERDIR}"/Foo.java
BAR="${OTHERDIR}"/Bar.java
rm -f "${FOO}" "${BAR}"

cat << EOF > "${FOO}"
  public class Foo {
      public static boolean doSomething() {
          try {
              Bar b = new Bar();
              return true;
          } catch (NoClassDefFoundError x) {
              return false;
          }
      }
  }
EOF

echo "public class Bar { }" > "${BAR}"

(cd "${OTHERDIR}"; \
  $JAVAC ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} Foo.java Bar.java; \
  $JAR ${TESTTOOLVMOPTS} cf "${OTHERDIR}"/Bar.jar Bar.class; \
  rm -f Bar.class)

# Create the manifest
MANIFEST="${TESTCLASSES}"/agent.mf
rm -f "${MANIFEST}"
echo "Premain-Class: ClassUnloadTest" > "${MANIFEST}"

# Setup test case as an agent
$JAR ${TESTTOOLVMOPTS} -cfm "${TESTCLASSES}"/ClassUnloadTest.jar "${MANIFEST}" \
  -C "${TESTCLASSES}" ClassUnloadTest.class

# Finally we run the test
(cd "${TESTCLASSES}"; \
  $JAVA ${TESTVMOPTS} ${TESTJAVAOPTS} -Xlog:class+unload \
    -javaagent:ClassUnloadTest.jar ClassUnloadTest "${OTHERDIR}" Bar.jar)
