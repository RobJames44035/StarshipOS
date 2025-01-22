#!/bin/ksh -p

#
# StarshipOS Copyright (c) 2015-2025. R.A. James
#

#
#   @test       EventQueuePushAutoshutdown.sh
#   @bug        8081485
#   @summary    tests that a program terminates automatically
#               after EventQueue.push()
#   @author     Anton Nashatyrev : area=toolkit
#
#   @compile EventQueuePushAutoshutdown.java
#   @run shell EventQueuePushAutoshutdown.sh


# Beginning of subroutines:
status=1

#Call this from anywhere to fail the test with an error message
# usage: fail "reason why the test failed"
fail()
 { echo "The test failed :-("
   echo "$*" 1>&2
   echo "exit status was $status"
   exit $status
 } #end of fail()

#Call this from anywhere to pass the test with a message
# usage: pass "reason why the test passed if applicable"
pass()
 { echo "The test passed!!!"
   echo "$*" 1>&2
   exit 0
 } #end of pass()

# end of subroutines


# The beginning of the script proper
OS=`uname -s`
case "$OS" in
   AIX | CYGWIN* | Darwin | Linux )
      FILESEP="/"
      ;;

   Windows_95 | Windows_98 |  Windows_NT | Windows_ME )
      FILESEP="\\"
      ;;

   # catch all other OSs
   * )
      echo "Unrecognized system!  $OS"
      fail "Unrecognized system!  $OS"
      ;;
esac


# Want this test to run standalone as well as in the harness, so do the
#  following to copy the test's directory into the harness's scratch directory
#  and set all appropriate variables:

if [ -z "${TESTJAVA}" ] ; then
   # TESTJAVA is not set, so the test is running stand-alone.
   # TESTJAVA holds the path to the root directory of the build of the JDK
   # to be tested.  That is, any java files run explicitly in this shell
   # should use TESTJAVA in the path to the java interpreter.
   # So, we'll set this to the JDK spec'd on the command line.  If none
   # is given on the command line, tell the user that and use a cheesy
   # default.
   # THIS IS THE JDK BEING TESTED.
   if [ -n "$1" ] ;
      then TESTJAVA=$1
      else fail "no JDK specified on command line!"
   fi
   TESTSRC=.
   TESTCLASSES=.
   STANDALONE=1;
fi
echo "JDK under test is: $TESTJAVA"

#Deal with .class files:
if [ -n "${STANDALONE}" ] ;
   then
   #if standalone, remind user to cd to dir. containing test before running it
   echo "Just a reminder: cd to the dir containing this test when running it"
   # then compile all .java files (if there are any) into .class files
   if [ -a *.java ] ;
      then echo "Reminder, this test should be in its own directory with all"
      echo "supporting files it needs in the directory with it."
      ${TESTJAVA}/bin/javac ./*.java ;
   fi
   # else in harness so copy all the class files from where jtreg put them
   # over to the scratch directory this test is running in.
   else cp ${TESTCLASSES}/*.class . ;
fi

#if in test harness, then copy the entire directory that the test is in over
# to the scratch directory.  This catches any support files needed by the test.
if [ -z "${STANDALONE}" ] ;
   then cp ${TESTSRC}/* .
fi

#Just before executing anything, make sure it has executable permission!
chmod 777 ./*

###############  YOUR TEST CODE HERE!!!!!!!  #############

#All files required for the test should be in the same directory with
# this file.  If converting a standalone test to run with the harness,
# as long as all files are in the same directory and it returns 0 for
# pass, you should be able to cut and paste it into here and it will
# run with the test harness.

${TESTJAVA}/bin/java EventQueuePushAutoshutdown

###############  END YOUR TEST CODE !!!!! ############
#Be sure the last command executed above this line returns 0 for success,
# something non-zero for failure.
status=$?

# pass or fail the test based on status of the command
case "$status" in
   0 )
      pass ""
      ;;

   1 )
      fail "The program didn't automatically shut down"
      ;;

   * )
      fail "The program terminated unexpectedly!"
      ;;
esac

#For additional examples of how to write platform independent KSH scripts,
# see the jtreg file itself.  It is a KSH script for both Solaris and Win32
