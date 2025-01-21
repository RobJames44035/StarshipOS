#!/bin/sh

#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#


# @test
# @bug 6265810 6705893
# @build CheckEngine
# @run shell jrunscript-argsTest.sh
# @summary Test passing of script arguments from command line

. ${TESTSRC-.}/common.sh

setup
${JAVA} ${TESTVMOPTS} ${TESTJAVAOPTS} -cp ${TESTCLASSES} CheckEngine
if [ $? -eq 2 ]; then
    echo "No js engine found and engine not required; test vacuously passes."
    exit 0
fi

# we check whether "excess" args are passed as script arguments

${JRUNSCRIPT} -l nashorn -J-Djava.awt.headless=true -f - hello world <<EOF

if (typeof(arguments) == 'undefined') { println("arguments expected"); exit(1); }

if (arguments.length != 2) { println("2 arguments are expected here"); exit(1); }

if (arguments[0] != 'hello') { println("First arg should be 'hello'"); exit(1); }
  
if (arguments[1] != 'world') { println("Second arg should be 'world'"); exit(1); }

println("Passed");
exit(0);
EOF

if [ $? -ne 0 ]; then
    exit 1
fi
