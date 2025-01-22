#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#

# @test
# @bug 6291034
# @run shell DeleteOnExitTest.sh
# @summary Verify that temporary imageio files are deleted on VM exit.

if [ -z "${TESTSRC}" ]; then
  echo "TESTSRC undefined: defaulting to ."
  TESTSRC=.
fi

if [ -z "${TESTCLASSES}" ]; then
  echo "TESTCLASSES undefined: defaulting to ."
  TESTCLASSES=.
fi

if [ -z "${TESTJAVA}" ]; then
  echo "TESTJAVA undefined: can't continue."
  exit 1
fi

echo "TESTJAVA=${TESTJAVA}"
echo "TESTSRC=${TESTSRC}"
echo "TESTCLASSES=${TESTCLASSES}"
cd ${TESTSRC}
${COMPILEJAVA}/bin/javac -d ${TESTCLASSES} DeleteOnExitTest.java

cd ${TESTCLASSES}

numfiles0=`ls ${TESTCLASSES} | grep "imageio*.tmp" | wc -l`

${TESTJAVA}/bin/java ${TESTVMOPTS} \
    -Djava.io.tmpdir=${TESTCLASSES} DeleteOnExitTest

if [ $? -ne 0 ]
    then
      echo "Test fails: exception thrown!"
      exit 1
fi

numfiles1=`ls ${TESTCLASSES} | grep "imageio*.tmp" | wc -l`

if [ $numfiles0 -ne $numfiles1 ]
    then
      echo "Test fails: tmp file exists!"
      exit 1
fi
echo "Test passed."
exit 0
