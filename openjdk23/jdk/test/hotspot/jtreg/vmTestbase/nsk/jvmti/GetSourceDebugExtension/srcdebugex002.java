/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetSourceDebugExtension;

import java.io.*;

/**
 * This test checks that the JVMTI function <code>GetSourceDebugExtension()</code>
 * correctly returns the following errors:
 * <li><i>JVMTI_ERROR_NULL_POINTER</i>
 * <li><i>JVMTI_ERROR_INVALID_CLASS</i>.
 */
public class srcdebugex002 {
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int JCK_STATUS_BASE = 95;

    static boolean DEBUG_MODE = false;
    private PrintStream out;

    static {
        try {
            System.loadLibrary("srcdebugex002");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Could not load srcdebugex002 library");
            System.err.println("java.library.path:" +
                System.getProperty("java.library.path"));
            throw e;
        }
    }

    native static int getSrcDebugX(int t_case);

    public static void main(String[] argv) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        System.exit(run(argv, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream out) {
        return new srcdebugex002().runIt(argv, out);
    }

    private int runIt(String argv[], PrintStream out) {
        int retValue = 0;
        int totRes = PASSED;

        this.out = out;
        for (int i=0; i < argv.length; i++) {
            if (argv[i].equals("-v")) // verbose mode
                DEBUG_MODE = true;
        }

/* check that GetSourceDebugExtension(), being invoked with NULL pointer
   to the string, returns the appropriate error JVMTI_ERROR_NULL_POINTER */
        if (DEBUG_MODE)
            totRes = retValue = getSrcDebugX(1);
        else
            totRes = retValue = getSrcDebugX(0);
        if (DEBUG_MODE && retValue == PASSED)
            out.println("Check #1 PASSED:\n" +
                "\tGetSourceDebugExtension(), being invoked with NULL pointer " +
                "to the string,\n" +
                "\treturned the appropriate error JVMTI_ERROR_NULL_POINTER");

/* check that GetSourceDebugExtension(), being invoked with invalid class,
   returns the appropriate error JVMTI_ERROR_INVALID_CLASS */
        if (DEBUG_MODE)
            retValue = getSrcDebugX(3);
        else
            retValue = getSrcDebugX(2);
        if (retValue == FAILED)
            totRes = FAILED;
        else
            if (DEBUG_MODE && retValue == PASSED)
                out.println("Check #2 PASSED:\n" +
                    "\tGetSourceDebugExtension(), being invoked with"
                    + " invalid pointer to the class,\n"
                    + "\treturned the appropriate error JVMTI_ERROR_INVALID_CLASS");

        return totRes;
    }
}
