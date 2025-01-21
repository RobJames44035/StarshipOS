/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SuspendThreadList;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class suspendthrdlst002 extends DebugeeClass {

    // load native library if required
    static {
        System.loadLibrary("suspendthrdlst002");
    }

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new suspendthrdlst002().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    // constants
    public static final int DEFAULT_THREADS_COUNT = 10;

    // tested thread
    suspendthrdlst002Thread threads[] = null;
    int threadsCount = 0;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        threadsCount = argHandler.findOptionIntValue("threads", DEFAULT_THREADS_COUNT);

        // create tested threads
        threads = new suspendthrdlst002Thread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new suspendthrdlst002Thread("TestedThread #" + i);
        }

        // run tested threads
        log.display("Staring tested threads");
        try {
            for (int i = 0; i < threadsCount; i++) {
                threads[i].start();
                if (!threads[i].checkReady()) {
                    throw new Failure("Unable to prepare tested thread: " + threads[i]);
                }
            }

            // testing sync
            log.display("Sync: thread started");
            status = checkStatus(status);
        } finally {
            // let threads to finish
            for (int i = 0; i < threadsCount; i++) {
                threads[i].letFinish();
            }
        }

        // wait for thread to finish
        log.display("Finishing tested threads");
        try {
            for (int i = 0; i < threadsCount; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        // testing sync
        log.display("Sync: thread finished");
        status = checkStatus(status);

        return status;
    }
}

/* =================================================================== */

// basic class for tested threads
class suspendthrdlst002Thread extends Thread {
    private volatile boolean threadReady = false;
    private volatile boolean shouldFinish = false;

    // make thread with specific name
    public suspendthrdlst002Thread(String name) {
        super(name);
    }

    // run thread continuously
    public void run() {
        // run in a loop
        threadReady = true;
        int i = 0;
        int n = 1000;
        while (!shouldFinish) {
            if (n <= 0) {
                n = 1000;
            }
            if (i > n) {
                i = 0;
                n = n - 1;
            }
            i = i + 1;
            Thread.yield();
        }
    }

    // check if thread is ready
    public boolean checkReady() {
        try {
            while (!threadReady) {
                sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new Failure("Interruption while preparing tested thread: \n\t" + e);
        }
        return threadReady;
    }

    // let thread to finish
    public void letFinish() {
        shouldFinish = true;
    }
}
