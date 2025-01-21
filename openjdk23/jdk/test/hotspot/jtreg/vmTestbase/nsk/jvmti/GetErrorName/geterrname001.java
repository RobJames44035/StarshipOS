/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetErrorName;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class geterrname001 extends DebugeeClass {

    // load native library if required
    static {
        loadLibrary("geterrname001");
    }

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new geterrname001().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    int status = Consts.TEST_PASSED;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);

        log.display("Sync: debuggee started");

        return checkStatus(status);
    }
}
