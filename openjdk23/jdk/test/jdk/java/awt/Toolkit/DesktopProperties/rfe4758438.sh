#
# StarshipOS Copyright (c) 2014-2025. R.A. James
#

if [ -z "${TESTJAVA}" ]; then
  echo "TESTJAVA undefined: can't continue."
  exit 1
fi

OS=`uname`

case "$OS" in
    Linux* )
        ;;
    * )
        echo "This Feature is not to be tested on $OS"
        exit 0
        ;;
esac

printf "\n/* Test env:\n\n"
env
printf "\n*/\n\n"

XDG_GNOME=$(echo $XDG_CURRENT_DESKTOP | grep -i gnome)

if [ -z "$XDG_GNOME" ] \
     && [ ${GNOME_DESKTOP_SESSION_ID:-nonset} = "nonset" ] \
     && [ ${GNOME_SESSION_NAME:-nonset} = "nonset" ]
then
    echo "This test should run under Gnome"
    exit 0
fi

SCHEMAS=`gsettings list-schemas | wc -l`

if [ $SCHEMAS -eq 0 ];
then
    TOOL=`which gconftool-2`
    USE_GSETTINGS="false"
else
    TOOL=`which gsettings`
    USE_GSETTINGS="true"
fi

cd ${TESTSRC}
echo $PWD
echo "${TESTJAVA}/bin/javac -d ${TESTCLASSES} rfe4758438.java"

set -e
${TESTJAVA}/bin/javac -d ${TESTCLASSES} rfe4758438.java
set +e


cd ${TESTCLASSES}
${TESTJAVA}/bin/java -DuseGsettings=${USE_GSETTINGS} -Dtool=${TOOL} ${TESTVMOPTS} rfe4758438

if [ $? -ne 0 ]
then
    echo "Test failed. See the error stream output"
    exit 1
fi
exit 0
