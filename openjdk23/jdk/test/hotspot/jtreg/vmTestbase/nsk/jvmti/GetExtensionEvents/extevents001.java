/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetExtensionEvents;

import java.io.PrintStream;
import java.lang.ref.SoftReference;

import nsk.share.*;
import nsk.share.jvmti.*;

public class extevents001 extends DebugeeClass {

    /** Load native library if required. */
    static {
        loadLibrary("extevents001");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new extevents001().runIt(argv, out);
    }

    /* =================================================================== */

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    /* constants */
    public static final int DEFAULT_OBJECTS_COUNT = 100;

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        log.display("Sync: debugee class ready");
        status = checkStatus(status);

        return status;
    }
}
