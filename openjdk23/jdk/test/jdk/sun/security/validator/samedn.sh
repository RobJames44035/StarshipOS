#
# StarshipOS Copyright (c) 2010-2025. R.A. James
#

# @test
# @bug 6958869
# @summary regression: PKIXValidator fails when multiple trust anchors
# have same dn
# @modules java.base/sun.security.validator
#

if [ "${TESTSRC}" = "" ] ; then
  TESTSRC="."
fi
if [ "${TESTJAVA}" = "" ] ; then
  JAVAC_CMD=`which javac`
  TESTJAVA=`dirname $JAVAC_CMD`/..
  COMPILEJAVA="${TESTJAVA}"
fi

# set platform-dependent variables
OS=`uname -s`
case "$OS" in
  Windows_* )
    FS="\\"
    ;;
  * )
    FS="/"
    ;;
esac

KT="$TESTJAVA${FS}bin${FS}keytool ${TESTTOOLVMOPTS} -storepass changeit \
    -keypass changeit -keystore samedn.jks -keyalg rsa"
JAVAC=$COMPILEJAVA${FS}bin${FS}javac
JAVA=$TESTJAVA${FS}bin${FS}java

rm -rf samedn.jks 2> /dev/null

# 1. Generate 3 aliases in a keystore: ca1, ca2, user. The CAs' startdate
# is set to one year ago so that they are expired now

$KT -genkeypair -alias ca1 -dname CN=CA -keyalg rsa -sigalg md5withrsa -ext bc -startdate -1y
$KT -genkeypair -alias ca2 -dname CN=CA -keyalg rsa -sigalg sha1withrsa -ext bc -startdate -1y
$KT -genkeypair -alias user -dname CN=User -keyalg rsa

# 2. Signing: ca -> user. The startdate is set to 1 minute in the past to ensure the certificate
# is valid at the time of validation and to prevent any issues with timing discrepancies

$KT -certreq -alias user | $KT -gencert -rfc -alias ca1 -startdate -1M > samedn1.certs
$KT -certreq -alias user | $KT -gencert -rfc -alias ca2 -startdate -1M > samedn2.certs

# 3. Append the ca file

$KT -export -rfc -alias ca1 >> samedn1.certs
$KT -export -rfc -alias ca2 >> samedn2.certs

# 4. Remove user for cacerts

$KT -delete -alias user

# 5. Build and run test. Make sure the CA certs are ignored for validity check.
# Check both, one of them might be dropped out of map in old codes.

EXTRAOPTS="--add-exports java.base/sun.security.validator=ALL-UNNAMED"
$JAVAC ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} ${EXTRAOPTS} -d . ${TESTSRC}${FS}CertReplace.java
$JAVA ${TESTVMOPTS} ${TESTJAVAOPTS} ${EXTRAOPTS} CertReplace samedn.jks samedn1.certs || exit 1
$JAVA ${TESTVMOPTS} ${TESTJAVAOPTS} ${EXTRAOPTS} CertReplace samedn.jks samedn2.certs || exit 2
