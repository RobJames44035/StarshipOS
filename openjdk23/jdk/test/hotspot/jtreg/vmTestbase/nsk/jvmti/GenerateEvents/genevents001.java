/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GenerateEvents;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class genevents001 extends DebugeeClass {

    /** Load native library if required. */
    static {
        loadLibrary("genevents001");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new genevents001().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    /* Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        log.display("Intensively call tested method to provoke compilation");
        try {
            genevents001Thread thread = new genevents001Thread();
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            throw new Failure("Interruption while running tested thread: \n\t" + e);
        }

        log.display("Testing sync: method forced to compile");
        status = checkStatus(status);

        return status;
    }
}

/* =================================================================== */

/** Thread with method to be compiled. */
class genevents001Thread extends Thread {
    public void run() {
        // invoke tested method in a loop to provoke compilation
        for (int i = 0; i < 100; i++) {
            testedMethod(i);
        }
    }

    public int testedMethod(int i) {
        int k = 0;
        for (int j = 0; j < i; j++) {
            k += (i - j);
        }
        return k;
    }
}
