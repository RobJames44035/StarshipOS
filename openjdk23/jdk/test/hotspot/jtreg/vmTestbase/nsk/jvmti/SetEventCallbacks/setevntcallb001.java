/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SetEventCallbacks;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class setevntcallb001 extends DebugeeClass {

    /** Load native library if required. */
    static {
        loadLibrary("setevntcallb001");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new setevntcallb001().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    int status = Consts.TEST_PASSED;

    /* Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);

        try {
            setevntcallb001Thread thread = new setevntcallb001Thread();
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            throw new Failure("Main thread interrupted while waiting for tested thread:\n\t"
                                + e);
        }

        log.display("Testing sync: tested thread started and finished");
        status = checkStatus(status);

        return status;
    }
}

/* =================================================================== */

/** Thread to generate tested events. */
class setevntcallb001Thread extends Thread {
    public void run() {
        // do nothing special
    }
}
