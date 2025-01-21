/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.multienv.MA05;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class ma05t001 extends DebugeeClass {

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new ma05t001().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    int status = Consts.TEST_PASSED;
    Log log = null;
    long timeout = 0;

    // tested thread
    ma05t001Thread thread = null;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000;

        thread = new ma05t001Thread("Debuggee Thread");
        thread.start();
        thread.startingBarrier.waitFor();
        status = checkStatus(status);
        thread.waitingBarrier.unlock();

        // wait for thread finish
        try {
            thread.join(timeout);
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        log.display("Debugee finished");
        return checkStatus(status);
    }
}

/* =================================================================== */

class ma05t001Thread extends Thread {
    public Wicket startingBarrier = new Wicket();
    public Wicket waitingBarrier = new Wicket();

    public ma05t001Thread(String name) {
        super(name);
    }

    public void run() {
        startingBarrier.unlock();
        waitingBarrier.waitFor();
        for (int i = 0; i < 3; i++) {
            checkPoint();
        }
    }

    // dummy method to be breakpointed by agent
    public void checkPoint() {
    }
}
