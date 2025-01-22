#!/bin/sh

#
# StarshipOS Copyright (c) 2015-2025. R.A. James
#

checkVariable() {
  variable='$'$1

  if [ "${variable}" = "" ]; then
    echo "Failed due to $1 is not set."
    exit 1
  fi
}

checkVariables() {
  for variable in $*
  do
    checkVariable ${variable}
  done
}

# Script needs parameters
if [ $# = 0 ]; then
  echo "Syntax: IsolatedJDK.sh <Suffix> [remove]"
  exit 1
fi

# Is it the call to remove ?
if [ $# = 2 ]; then
  if [ "$2" = "remove" ]; then
    removeIsolatedJdk=1
  fi
fi

# Check essential variables
checkVariables TESTJAVA
ISOLATED_JDK="./ISOLATED_JDK_$1"

# Remove isolated copy
if [ "$removeIsolatedJdk" = "1" ]; then
  echo "Removing ${ISOLATED_JDK}..."
  rm -rf ${ISOLATED_JDK}
  echo "Removed."
  exit 0
fi

# Make an isolated copy of the testing JDK
echo "Copying test JDK: ${TESTJAVA} -> ${ISOLATED_JDK}..."
cp -H -R ${TESTJAVA} ${ISOLATED_JDK} || exit 1
chmod -R +w ${ISOLATED_JDK} || exit 1
echo "Copy done."
