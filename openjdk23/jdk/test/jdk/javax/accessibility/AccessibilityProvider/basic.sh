#
# StarshipOS Copyright (c) 2015-2025. R.A. James
#

# Command-line usage: sh basic.sh /path/to/build

if [ -z "$TESTJAVA" ]; then
  if [ $# -lt 1 ]; then exit 1; fi
  TESTJAVA="$1"
  TESTSRC=`pwd`
  TESTCLASSES="`pwd`"
fi

JAVA="$TESTJAVA/bin/java"

OS=`uname -s`
case "$OS" in
    Darwin | AIX )
      FS='/'
      SEP=':' ;;
    Linux )
      FS='/'
      SEP=':' ;;
    * )
      FS='\\'
      SEP='\;' ;;
esac

TESTD=x.test
rm -rf $TESTD
mkdir -p $TESTD

mv $TESTCLASSES/FooProvider.class $TESTD
mv $TESTCLASSES/BarProvider.class $TESTD
mv $TESTCLASSES/UnusedProvider.class $TESTD
mkdir -p $TESTD/META-INF/services
echo FooProvider >$TESTD/META-INF/services/javax.accessibility.AccessibilityProvider
echo BarProvider >>$TESTD/META-INF/services/javax.accessibility.AccessibilityProvider
echo UnusedProvider >>$TESTD/META-INF/services/javax.accessibility.AccessibilityProvider


failures=0

go() {
  CP="$TESTCLASSES$SEP$TESTD"
  echo ''
  sh -xc "$JAVA -Djavax.accessibility.assistive_technologies=$PROVIDER1$COMMA$PROVIDER2 -cp $CP Load $1 $2 $3" 2>&1
  if [ $? != 0 ]; then failures=`expr $failures + 1`; fi
}

# find one provider
PROVIDER1="FooProvider"
go pass $PROVIDER1

# fail if no provider found
PROVIDER1="NoProvider"
go fail $PROVIDER1

# pass if none provider found
PROVIDER1=
go pass $PROVIDER1

PROVIDER1=" "
go pass $PROVIDER1

# setup for two providers
COMMA=","

# find two providers, both exist
PROVIDER1="FooProvider"
PROVIDER2="BarProvider"
go pass $PROVIDER1 $PROVIDER2

# find two providers, where second one doesn't exist
PROVIDER1="FooProvider"
PROVIDER2="NoProvider"
go fail $PROVIDER1 $PROVIDER2

# find two providers, where first one doesn't exist
PROVIDER1="NoProvider"
PROVIDER2="BarProvider"
go fail $PROVIDER1 $PROVIDER2

echo ''
if [ $failures -gt 0 ];
  then echo "$failures case(s) failed";
  else echo "All cases passed"; fi
exit $failures

