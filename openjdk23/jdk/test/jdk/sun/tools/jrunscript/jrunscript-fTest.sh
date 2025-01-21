#!/bin/sh

#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#


# @test
# @bug 6265810 6705893
# @build CheckEngine
# @run shell jrunscript-fTest.sh
# @summary Test that output of 'jrunscript -f' matches the dash-f.out file

. ${TESTSRC-.}/common.sh

setup
${JAVA} ${TESTVMOPTS} ${TESTJAVAOPTS} -cp ${TESTCLASSES} CheckEngine
if [ $? -eq 2 ]; then
    echo "No js engine found and engine not required; test vacuously passes."
    exit 0
fi

# -f option used with JavaScript as language chosen explicitly
# with -l option

rm -f jrunscript-fTest.out 2>/dev/null
${JRUNSCRIPT} -J-Dnashorn.args.prepend=--no-deprecation-warning -J-Djava.awt.headless=true -l nashorn -f ${TESTSRC}/hello.js > jrunscript-fTest.out 2>&1

$golden_diff jrunscript-fTest.out ${TESTSRC}/dash-f.out
if [ $? != 0 ]
then
  echo "Output of jrunscript -f differ from expected output. Failed."
  rm -f jrunscript-fTest.out 2>/dev/null
  exit 1
fi

rm -f jrunscript-fTest.out
echo "Passed"
exit 0
