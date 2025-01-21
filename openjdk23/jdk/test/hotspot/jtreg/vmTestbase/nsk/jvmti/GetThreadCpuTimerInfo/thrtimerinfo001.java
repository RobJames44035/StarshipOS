/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetThreadCpuTimerInfo;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

/** Debuggee class for this test. */
public class thrtimerinfo001 extends DebugeeClass {

    /** Load native library if required. */
    static {
        loadLibrary("thrtimerinfo001");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new thrtimerinfo001().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    /** Run debuggee. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        thrtimerinfo001Thread thread = new thrtimerinfo001Thread("TestedThread");

        // sync before thread started
        log.display("Sync: tested thread created");
        status = checkStatus(status);

        // start and finish tested thread
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            throw new Failure("Main thread interrupted while waiting for tested thread:\n\t"
                                + e);
        }

        // sync after thread finished
        log.display("Sync: tested thread started and finished");
        status = checkStatus(status);

        return status;
    }
}

/* =================================================================== */

/** Class for tested thread. */
class thrtimerinfo001Thread extends Thread {
    /** Make thread with specific name. */
    public thrtimerinfo001Thread(String name) {
        super(name);
    }

    /** Run some code. */
    public void run() {
        // do something
        int n = 1000;
        int s = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                s += i * 10;
            } else {
                s -= i * 10;
            }
        }
    }
}
