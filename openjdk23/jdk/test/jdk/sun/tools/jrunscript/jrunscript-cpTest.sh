#!/bin/sh

#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#


# @test
# @bug 6265810 6705893
# @build CheckEngine
# @run shell jrunscript-cpTest.sh
# @summary Test -cp option to set classpath

. ${TESTSRC-.}/common.sh

setup
${JAVA} ${TESTVMOPTS} ${TESTJAVAOPTS} -cp ${TESTCLASSES} CheckEngine
if [ $? -eq 2 ]; then
    echo "No js engine found and engine not required; test vacuously passes."
    exit 0
fi

rm -f Hello.class
${JAVAC} ${TESTTOOLVMOPTS} ${TESTJAVACOPTS} ${TESTSRC}/Hello.java -d .

# we check whether classpath setting for app classes
# work with jrunscript. Script should be able to
# access Java class "Hello".

${JRUNSCRIPT} -l nashorn -cp . <<EOF
var v;  
try { v = new Packages.Hello(); } catch (e) { println(e); exit(1) }
if (v.string != 'hello') { println("Unexpected property value"); exit(1); }
EOF

if [ $? -ne 0 ]; then
   exit 1
fi

# -classpath and -cp are synonyms

${JRUNSCRIPT} -l nashorn -classpath . <<EOF
var v;
try { v = new Packages.Hello(); } catch (e) { println(e); exit(1) }
if (v.string != 'hello') { println("unexpected property value"); exit(1); }
EOF

if [ $? -ne 0 ]; then
   exit 1
fi

rm -f Hello.class
echo "Passed"
exit 0
