#
# StarshipOS Copyright (c) 2015-2025. R.A. James
#

# @test
# @bug 8081729 8140314
# @summary Test external plugin as classpath jar and as a modular jar.

set -e

exception=0

if [ -z "$TESTJAVA" ]; then
  if [ $# -lt 1 ]; then  echo "No Java path specified. Exiting."; fi
  if [ $# -lt 1 ]; then exit 1; fi
  TESTJAVA="$1"; shift
  COMPILEJAVA="${TESTJAVA}"
  TESTSRC="`pwd`"
  TESTCLASSES="`pwd`"
fi

JAVAC="$COMPILEJAVA/bin/javac"
JAR="$COMPILEJAVA/bin/jar"
JAVA="$TESTJAVA/bin/java ${TESTVMOPTS}"

TESTDIR="$TESTCLASSES/classes"
PLUGINDIR="$TESTCLASSES/classes"
mkdir -p $TESTDIR
$JAVAC -d $TESTDIR `find $TESTSRC/src/simptest -name "*.java"`

# compile the plugin java sources and services file into a temp location.

mkdir -p $TESTCLASSES/tmpdir/simp
cp -r $TESTSRC/src/simp/META-INF $TESTCLASSES/tmpdir
$JAVAC -d $TESTCLASSES/tmpdir `find $TESTSRC/src/simp -name "*.java"`

# create modular jar file (inc. module-info.java) from the class files.
mkdir -p $PLUGINDIR
$JAR cf $PLUGINDIR/simp.jar -C $TESTCLASSES/tmpdir META-INF/services \
    -C $TESTCLASSES/tmpdir module-info.class -C $TESTCLASSES/tmpdir simp

OS=`uname -s`
case "$OS" in
  Windows_* | CYGWIN* )
CPSEP=";"
  ;;
  * )
CPSEP=":"
  ;;
esac

# expect to find SimpReader via jar on classpath.
# Will be treated as a regular jar.
echo "Test classpath jar .. "
$JAVA  -cp ${TESTDIR}${CPSEP}${PLUGINDIR}/simp.jar simptest.TestSIMPPlugin
if [ $? -ne 0 ]; then
    exception=1
      echo "Classpath test failed: exception thrown!"
fi

# expect to find SimpReader on module path
echo "Test modular jar .. "
$JAVA --module-path $PLUGINDIR -cp $TESTDIR simptest.TestSIMPPlugin

if [ $? -ne 0 ]; then
    exception=1
    echo "modular jar test failed: exception thrown!"
fi

if [ $exception -ne 0 ]; then
    echo "TEST FAILED"
    exit 1
fi
exit 0
