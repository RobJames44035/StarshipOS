/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.DisposeEnvironment;

import java.io.PrintStream;
import java.lang.ref.SoftReference;

import nsk.share.*;
import nsk.share.jvmti.*;

public class disposeenv002 extends DebugeeClass {

    /** Load native library if required. */
    static {
        loadLibrary("disposeenv002");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new disposeenv002().runIt(argv, out);
    }

    /* =================================================================== */

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    int status = Consts.TEST_PASSED;

    /* constants */
    public static final int DEFAULT_OBJECTS_COUNT = 100;

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);

        log.display("Debugee finished");
        return status;
    }
}
