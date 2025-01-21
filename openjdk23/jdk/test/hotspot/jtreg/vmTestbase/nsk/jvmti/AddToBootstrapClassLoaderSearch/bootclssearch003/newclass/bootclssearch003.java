/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.AddToBootstrapClassLoaderSearch;

import java.io.PrintStream;

import nsk.share.Consts;

/** Debuggee class for this test. */
public class bootclssearch003 {

    /** Run test from command line. */
    public static void main(String argv[]) {
        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new bootclssearch003().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    int status = Consts.TEST_FAILED;

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        out.println("# ERROR: Class was loaded from directory added by AddToBootstrapClassLoaderSearch()");
        out.println("# ERROR: though this class is also available from usual class search path");
        return status;
    }
}
