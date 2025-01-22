#
# StarshipOS Copyright (c) 2005-2025. R.A. James
#

#

setup() {
    # Verify directory context variables are set
    if [ "${TESTJAVA}" = "" ] ; then
        echo "TESTJAVA not set. Test cannot execute.  Failed."
        exit 1
    fi

    if [ "${TESTCLASSES}" = "" ] ; then
        TESTCLASSES="."
    fi

    if [ "${TESTSRC}" = "" ] ; then
        TESTSRC="."
    fi

    OS=`uname -s`
    case ${OS} in
    Windows_*)
        PS=";"
        FS="\\"
        # MKS diff deals with trailing CRs automatically
        golden_diff="diff"
        ;;
    CYGWIN*)
        PS=":"
        FS="/"
        # Cygwin diff needs to be told to ignore trailing CRs
        golden_diff="diff --strip-trailing-cr"
        ;;
    *)
        PS=":"
        FS="/"
        # Assume any other platform doesn't have the trailing CR stuff
        golden_diff="diff"
        ;;
    esac

    JRUNSCRIPT="${TESTJAVA}/bin/jrunscript"
    JAVAC="${TESTJAVA}/bin/javac"
    JAVA="${TESTJAVA}/bin/java"
}
