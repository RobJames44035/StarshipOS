#
# StarshipOS Copyright (c) 2004-2025. R.A. James
#

# @test
# @bug 6189812 6380357 6632886 8249142
# @key intermittent
# @summary Verify that temporary font files are deleted on exit.

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
${TESTJAVA}/bin/javac -d ${TESTCLASSES} DeleteFont.java

cd ${TESTCLASSES}

numfiles0=`ls ${TESTCLASSES} | wc -l`
${TESTJAVA}/bin/java ${TESTVMOPTS} -Djava.io.tmpdir=${TESTCLASSES} DeleteFont

if [ $? -ne 0 ]
    then
      echo "Test fails: exception thrown!"
      exit 1
fi

numfiles1=`ls ${TESTCLASSES} | wc -l`

if [ $numfiles0 -ne $numfiles1 ]
    then
      echo "Test fails: tmp file exists!"
      ls ${TESTCLASSES}
      exit 1
fi
exit 0
