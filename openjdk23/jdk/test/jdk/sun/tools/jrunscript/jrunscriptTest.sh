#!/bin/sh

#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#


# @test
# @bug 6265810 6705893
# @build CheckEngine
# @run shell jrunscriptTest.sh
# @summary Test that output of 'jrunscript' interactive matches the repl.out file

. ${TESTSRC-.}/common.sh

setup
${JAVA} ${TESTVMOPTS} ${TESTJAVAOPTS} -cp ${TESTCLASSES} CheckEngine
if [ $? -eq 2 ]; then
    echo "No js engine found and engine not required; test vacuously passes."
    exit 0
fi

rm -f jrunscriptTest.out 2>/dev/null
${JRUNSCRIPT} -J-Dnashorn.args.prepend=--no-deprecation-warning -J-Djava.awt.headless=true -l nashorn > jrunscriptTest.out 2>&1 <<EOF
v = 2 + 5;
v *= 5; v.doubleValue();
v = v + " is the value";
if (v != 0) { println('yes v != 0'); }
java.lang.System.out.println('hello world from script');
new java.lang.Runnable() { run: function() { println('I am runnable'); }}.run();
EOF

$golden_diff jrunscriptTest.out ${TESTSRC}/repl.out
if [ $? != 0 ]
then
  echo "Output of jrunscript -l nashorn differ from expected output. Failed."
  rm -f jrunscriptTest.out 2>/dev/null
  exit 1
fi

rm -f jrunscriptTest.out
echo "Passed"
exit 0
