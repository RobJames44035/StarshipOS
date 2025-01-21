/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.events.EM02;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class em02t002 extends DebugeeClass {

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new em02t002().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    static Log log = null;
    Log.Logger logger;
    int status = Consts.TEST_PASSED;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        logger = new Log.Logger(log,"debuggee> ");

        for (int i = 0; i < 3; i++) {

            logger.display("generating events");

            ClassUnloader.eatMemory();

            int currStatus = em02t002.checkStatus(Consts.TEST_PASSED);
            if (currStatus != Consts.TEST_PASSED)
                status = currStatus;
        }

        return status;
    }

}
