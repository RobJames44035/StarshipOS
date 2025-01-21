/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SetThreadLocalStorage;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class setthrdstor001 extends DebugeeClass {

    // load native library if required
    static {
        System.loadLibrary("setthrdstor001");
    }

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new setthrdstor001().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    // tested thread
    setthrdstor001Thread thread = null;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        // create tested thread
        thread = new setthrdstor001Thread("TestedThread");

        // run tested thread
        log.display("Staring tested thread");
        try {
            synchronized (thread.endingMonitor) {
                synchronized (thread.runningMonitor) {
                    // start thread
                    synchronized (thread.startingMonitor) {
                        thread.start();
                        thread.startingMonitor.wait();
                    }

                    // testing sync
                    log.display("Sync: thread started");
                    status = checkStatus(status);

                    // wait for thread to run
                    thread.runningMonitor.wait();

                    // testing sync
                    log.display("Sync: thread ran");
                    status = checkStatus(status);
                }

                // let thread to finish
            }

            // wait for thread to finish
            log.display("Finishing tested thread");
            thread.join();
        } catch (InterruptedException e) {
            throw new Failure("Interruption while running tested thread: \n\t" + e);
        }

        return status;
    }
}

/* =================================================================== */

// basic class for tested threads
class setthrdstor001Thread extends Thread {
    public Object startingMonitor = new Object();
    public Object runningMonitor = new Object();
    public Object endingMonitor = new Object();

    // make thread with specific name
    public setthrdstor001Thread(String name) {
        super(name);
    }

    // run thread continuously
    public void run() {
        // notify about starting
        synchronized (startingMonitor) {
            startingMonitor.notifyAll();
        }

        // notify about running
        synchronized (runningMonitor) {
            runningMonitor.notifyAll();
        }

        // wait for finish permit
        synchronized (endingMonitor) {
            // just finish
        }
    }
}
