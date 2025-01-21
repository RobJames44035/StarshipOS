/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.Agent_OnLoad;

import java.io.PrintStream;

import nsk.share.*;

public class agentonload003 {

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new agentonload003().runIt(argv, out);
    }

    /* =================================================================== */

    /* scaffold objects */
    int status = Consts.TEST_PASSED;

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        return status;
    }
}
