/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.scenarios.events.EM05;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class em05t001 extends DebugeeClass {

    // load native library if required
    static {
        System.loadLibrary("em05t001");
    }

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new em05t001().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    // monitors for threads synchronization
    static Object endingMonitor = new Object();

    // tested threads list
    static em05t001Thread thread = null;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        // create threads list
        thread = new em05t001Thread();

        // testing sync
        log.display("Testing sync: thread created");
        status = checkStatus(status);

        // run thread
        log.display("Run tested thread");
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        // testing sync
        log.display("Testing sync: thread finished");
        status = checkStatus(status);

        return status;
    }
}

/* =================================================================== */

// tested threads
class em05t001Thread extends Thread {
    public void run() {
        // invoke methods in a loop to provoke compilation
        for (int i = 0; i < 100; i++) {
            javaMethod(i);
            nativeMethod(i);
        }
    }

    public int javaMethod(int i) {
        int k = 0;
        for (int j = 0; j < i; j++) {
            k += (i - j);
        }
        return k;
    }

    public native int nativeMethod(int i);
}
