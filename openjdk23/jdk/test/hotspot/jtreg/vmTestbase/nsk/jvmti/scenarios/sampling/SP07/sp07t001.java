/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.sampling.SP07;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class sp07t001 extends DebugeeClass {

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new sp07t001().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    int status = Consts.TEST_PASSED;
    long timeout = 0;

    // tested thread
    sp07t001Thread thread = null;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000;
        log.display("Timeout = " + timeout + " msc.");

        thread = new sp07t001Thread("Debuggee Thread");
        thread.start();
        thread.startingBarrier.waitFor();
        status = checkStatus(status);
        thread.letItGo();

        try {
            thread.join(timeout);
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        log.display("Debugee finished: i = " + thread.i + ", n = " + thread.n);

        return status;
    }
}

/* =================================================================== */

class sp07t001Thread extends Thread {
    public Wicket startingBarrier = new Wicket();
    private volatile boolean flag = true;
    public int i, n;

    public sp07t001Thread(String name) {
        super(name);
    }

    public void run() {
        startingBarrier.unlock();

        n = 0;
        for (i = 0; flag; i++) {
            n = n + wrapper(i);
        }
    }

    // wrap fibonacci method
    native int wrapper(int i);

    // calculate Fibonacci Numbers
    int fibonacci(int n) {
        if (n <= 2) {
            return 1;
        } else {
            return wrapper(n-1) + wrapper(n-2);
        }
    }

    public void letItGo() {
        flag = false;
    }
}
