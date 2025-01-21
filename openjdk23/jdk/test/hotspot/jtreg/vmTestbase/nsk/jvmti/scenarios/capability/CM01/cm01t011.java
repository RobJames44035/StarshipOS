/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.scenarios.capability.CM01;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class cm01t011 extends DebugeeClass {

    // load native library if required
    static {
        loadLibrary("cm01t011");
    }

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new cm01t011().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    int status = Consts.TEST_PASSED;
    static long timeout = 0;

    // tested thread
    cm01t011Thread thread = null;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60000; // milliseconds
        log.display("Timeout = " + timeout + " msc.");

        thread = new cm01t011Thread("Debuggee Thread");

        // run thread
        try {
            // start thread
            synchronized (thread.startingMonitor) {
                thread.start();
                thread.startingMonitor.wait(timeout);
            }
            if (!thread.checkReady()) {
                throw new Failure("Unable to run thread " + thread);
            }

            // testing sync
            log.display("Testing sync: thread ready");
            if ((status = checkStatus(status)) != Consts.TEST_PASSED)
                return status;
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        log.display("Testing sync: thread finish");
        thread.letFinish();

        // wait for thread finish
        try {
            thread.join(timeout);
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        log.display("Testing sync: debuggee exit");
        return status;
    }
}

/* =================================================================== */

class cm01t011Thread extends Thread {
    public Object startingMonitor = new Object();
    private Object waitingMonitor = new Object();

    public cm01t011Thread(String name) {
        super(name);
    }

    public void run() {
        synchronized (waitingMonitor) {

            Object o = new Object();
            int i = 1;
            long l = 2;
            float f = 3.0F;
            double d = 4.0;

            // notify about starting
            synchronized (startingMonitor) {
                startingMonitor.notify();
            }

            // wait on monitor
            try {
                waitingMonitor.wait(cm01t011.timeout);
            } catch (InterruptedException ignore) {
                // just finish
            }
        }
    }

    public boolean checkReady() {
        // wait until waitingMonitor released on wait()
        synchronized (waitingMonitor) {
        }
        return true;
    }

    public void letFinish() {
        synchronized (waitingMonitor) {
            waitingMonitor.notify();
        }
    }
}
