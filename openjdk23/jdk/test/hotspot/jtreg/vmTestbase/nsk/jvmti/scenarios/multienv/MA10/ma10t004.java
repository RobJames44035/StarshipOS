/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.multienv.MA10;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class ma10t004 extends DebugeeClass {

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new ma10t004().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    int status = Consts.TEST_PASSED;
    Log log = null;
    long timeout = 0;

    // tested thread
    ma10t004Thread testedThread = null;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000;

        log.display("Debugee started");

        testedThread = new ma10t004Thread("Debuggee Thread");
        testedThread.start();

        testedThread.startingBarrier.waitFor();
        status = checkStatus(status);
        testedThread.waitingBarrier.unlock();

        try {
            testedThread.join(timeout);
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        log.display("Debugee finished");

        return checkStatus(status);
    }
}

/* =================================================================== */

class ma10t004Thread extends Thread {
    Wicket startingBarrier = new Wicket();
    Wicket waitingBarrier = new Wicket();

    public ma10t004Thread(String name) {
        super(name);
    }

    public synchronized void run() {
        startingBarrier.unlock();
        waitingBarrier.waitFor();
    }
}
