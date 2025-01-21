/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetSystemProperties;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class getsysprops001 extends DebugeeClass {

    /** Load native library if required. */
    static {
        loadLibrary("getsysprops001");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new getsysprops001().runIt(argv, out);
    }

    /* =================================================================== */

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    int status = Consts.TEST_PASSED;

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);

        // testing sync
        log.display("Sync: debugee ready");
        status = checkStatus(status);

        return status;
    }
}
