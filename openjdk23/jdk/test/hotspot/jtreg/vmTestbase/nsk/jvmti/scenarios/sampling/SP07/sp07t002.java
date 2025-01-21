/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.sampling.SP07;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class sp07t002 extends DebugeeClass {

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new sp07t002().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    int status = Consts.TEST_PASSED;
    long timeout = 0;

    // tested thread
    sp07t002Thread thread = null;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000;
        log.display("Timeout = " + timeout + " msc.");

        thread = new sp07t002Thread("Debuggee Thread");
        thread.start();
        thread.startingBarrier.waitFor();
        status = checkStatus(status);
        thread.letItGo();

        try {
            thread.join(timeout);
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        log.display("Debugee finished");

        return status;
    }
}

/* =================================================================== */

class sp07t002Thread extends Thread {
    final static int MAX_LADDER = 256;
    public Wicket startingBarrier = new Wicket();
    private volatile boolean flag = true;
    public volatile int depth = -1;

    public sp07t002Thread(String name) {
        super(name);
    }

    public void run() {
        startingBarrier.unlock();

        for (int i = 0; flag; i = (i + 1) % MAX_LADDER) {
            depth = i;
            catcher(i, MAX_LADDER - i);
        }
    }

    void catcher(int n, int m) {
        if (n > 0) {
            catcher(n - 1, m);
        } else {
            try {
                thrower(m);
            } catch (Exception e) {
            }
        }
    }

    void thrower(int n) throws Exception {
        if (n == 0) {
            throw new Exception();
        } else {
            thrower(n - 1);
        }
    }

    public void letItGo() {
        flag = false;
    }
}
