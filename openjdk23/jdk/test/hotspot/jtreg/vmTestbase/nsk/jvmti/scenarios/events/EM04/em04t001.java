/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.events.EM04;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class em04t001 extends DebugeeClass {

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new em04t001().runIt(argv, out);
    }

    /* =================================================================== */

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(out, argHandler);

        if (em04t001.checkStatus(Consts.TEST_PASSED) != Consts.TEST_PASSED)
            return Consts.TEST_FAILED;

        return Consts.TEST_PASSED;
    }

}
