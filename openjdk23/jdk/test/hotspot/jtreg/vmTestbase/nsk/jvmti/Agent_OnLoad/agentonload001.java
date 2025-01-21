/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.Agent_OnLoad;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class agentonload001 extends DebugeeClass {

    /** Load native library if required. */
    static {
        loadLibrary("agentonload001");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new agentonload001().runIt(argv, out);
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

        status = checkLoadStatus();
        if (status != Consts.TEST_PASSED) {
            log.complain("Agent_Onload() was not invoked on startup");
        }
        return status;
    }

    /** Check JVM_OnLoad status in native code. */
    private native int checkLoadStatus();
}
