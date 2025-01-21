/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.events.EM02;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class em02t009 extends DebugeeClass {

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new em02t009().runIt(argv, out);
    }

    /* =================================================================== */

    static final int STEP_NUMBER = 3;
    static final int NUMBER_OF_INVOCATIONS = 1000;
    static Log.Logger logger;

    // run debuggee
    public int runIt(String args[], PrintStream out) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        logger = new Log.Logger(new Log(out, argHandler),"debuggee> ");
        int status = Consts.TEST_PASSED;

        for (int i = 0; i < STEP_NUMBER; i++) {
            for(int j = 0; j < NUMBER_OF_INVOCATIONS; j++) {
                javaMethod();
            }

            if (checkStatus(Consts.TEST_PASSED) == Consts.TEST_FAILED) {
                status = Consts.TEST_FAILED;
            }

        }

        return status;
    }

    int javaMethod() {
        int k = 1;
        return k;
    }

}
