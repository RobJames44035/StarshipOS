/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.IterateOverObjectsReachableFromObject;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class iterobjreachobj005 extends DebugeeClass {

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new iterobjreachobj005().runIt(argv, out);
    }

    /** Tested object. */
    public static iterobjreachobj005 object = new iterobjreachobj005();

    /* =================================================================== */

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    /** Run debugee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        status = checkStatus(status);
        return status;
    }
}

/* =================================================================== */
