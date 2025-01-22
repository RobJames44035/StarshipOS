#!/bin/sh

#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#


# @test
# @bug 6265810 6705893
# @build CheckEngine
# @run shell jrunscript-eTest.sh
# @summary Test that output of 'jrunscript -e' matches the dash-e.out file

. ${TESTSRC-.}/common.sh

setup
${JAVA} ${TESTVMOPTS} ${TESTJAVAOPTS} -cp ${TESTCLASSES} CheckEngine
if [ $? -eq 2 ]; then
    echo "No js engine found and engine not required; test vacuously passes."
    exit 0
fi

# -e option with JavaScript explicitly chosen as language

rm -f jrunscript-eTest.out 2>/dev/null
${JRUNSCRIPT} -J-Dnashorn.args.prepend=--no-deprecation-warning -J-Djava.awt.headless=true -l nashorn -e "println('hello')" > jrunscript-eTest.out 2>&1

$golden_diff jrunscript-eTest.out ${TESTSRC}/dash-e.out
if [ $? != 0 ]
then
  echo "Output of jrunscript -e differ from expected output. Failed."
  rm -f jrunscript-eTest.out 2>/dev/null
  exit 1
fi

rm -f jrunscript-eTest.out
echo "Passed"
exit 0
