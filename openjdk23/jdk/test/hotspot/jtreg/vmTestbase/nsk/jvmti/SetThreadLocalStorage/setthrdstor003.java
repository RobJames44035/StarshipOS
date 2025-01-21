/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SetThreadLocalStorage;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class setthrdstor003 extends DebugeeClass {

    // load native library if required
    static {
        System.loadLibrary("setthrdstor003");
    }

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new setthrdstor003().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    // tested thread
    setthrdstor003Thread thread = null;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        // create tested thread
        thread = new setthrdstor003Thread("TestedThread");

        // testing sync
        log.display("Sync: thread created");
        status = checkStatus(status);

        // run tested thread
        try {
            log.display("Staring tested thread");
            thread.start();
            log.display("Finishing tested thread");
            thread.join();
        } catch (InterruptedException e) {
            throw new Failure("Interruption while running tested thread: \n\t" + e);
        }

        // testing sync
        log.display("Sync: thread finished");
        status = checkStatus(status);

        return status;
    }
}

/* =================================================================== */

// basic class for tested threads
class setthrdstor003Thread extends Thread {
    // make thread with specific name
    public setthrdstor003Thread(String name) {
        super(name);
    }

    // run thread
    public void run() {
        // do something
        int s = 0;
        for (int i = 0; i < 1000; i++) {
            s += i;
            if (s > 1000)
                s = 0;
        }
    }
}
