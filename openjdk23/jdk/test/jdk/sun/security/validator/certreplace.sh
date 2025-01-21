#
# StarshipOS Copyright (c) 2010-2025. R.A. James
#

# @test
# @bug 6948803
# @summary CertPath validation regression caused by SHA1 replacement root
#  and MD2 disable feature
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
    -keypass changeit -keystore certreplace.jks -keyalg rsa"
JAVAC=$COMPILEJAVA${FS}bin${FS}javac
JAVA=$TESTJAVA${FS}bin${FS}java

rm -rf certreplace.jks 2> /dev/null

# 1. Generate 3 aliases in a keystore: ca, int, user

$KT -genkeypair -alias ca -dname CN=CA -keyalg rsa -sigalg md2withrsa -ext bc
$KT -genkeypair -alias int -dname CN=Int -keyalg rsa
$KT -genkeypair -alias user -dname CN=User -keyalg rsa

# 2. Signing: ca -> int -> user

$KT -certreq -alias int | $KT -gencert -rfc -alias ca -ext bc \
    | $KT -import -alias int
$KT -certreq -alias user | $KT -gencert -rfc -alias int \
    | $KT -import -alias user

# 3. Create the certchain file

$KT -export -alias user -rfc > certreplace.certs
$KT -export -rfc -alias int >> certreplace.certs
$KT -export -rfc -alias ca >> certreplace.certs

# 4. Upgrade ca from MD2withRSA to SHA256withRSA, remove other aliases and
# make this keystore the cacerts file

$KT -selfcert -alias ca
$KT -delete -alias int
$KT -delete -alias user

# 5. Build and run test

EXTRAOPTS="--add-exports java.base/sun.security.validator=ALL-UNNAMED"
$JAVAC ${TESTJAVACOPTS} ${TESTTOOLVMOPTS} ${EXTRAOPTS} -d . ${TESTSRC}${FS}CertReplace.java
$JAVA ${TESTVMOPTS} ${TESTJAVAOPTS} ${EXTRAOPTS} CertReplace certreplace.jks certreplace.certs
