#!/bin/sh

#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#


# @test
# @bug 6265810 6705893
# @build CheckEngine
# @run shell jrunscript-DTest.sh
# @summary Test that output of 'jrunscript -D' 

. ${TESTSRC-.}/common.sh

setup
${JAVA} ${TESTVMOPTS} ${TESTJAVAOPTS} -cp ${TESTCLASSES} CheckEngine
if [ $? -eq 2 ]; then
    echo "No js engine found and engine not required; test vacuously passes."
    exit 0
fi

# test whether value specifieD by -D option is passed
# to script as java.lang.System property.  sysProps is
# jrunscript shell built-in variable for System properties.

${JRUNSCRIPT} -l nashorn -Djrunscript.foo=bar <<EOF
if (sysProps["jrunscript.foo"] == "bar") { println("Passed"); exit(0); }
// unexpected value
println("Unexpected System property value");
exit(1);
EOF

if [ $? -ne 0 ]; then 
    exit 1
fi
