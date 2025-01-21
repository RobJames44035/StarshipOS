/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.allocation.AP10;

import java.io.*;

import nsk.share.*;
import nsk.share.jvmti.*;

public class ap10t001 extends DebugeeClass {
    /* number of interations to provoke garbage collecting */
    final static int ITERATIONS = 4;

    public static void main(String[] argv) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // produce JCK-like exit status
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream out) {
        return new ap10t001().runThis(argv, out);
    }

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    private int runThis(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        log.display("Sync: Debugee started");
        status = checkStatus(status);

        for (int i=0; i<ITERATIONS; i++)
            System.gc();

        log.display("Sync: GC provoked");
        status = checkStatus(status);
        return status;
    }
}
