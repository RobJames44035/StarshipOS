/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.Scenarios.invokeMethod;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import java.io.*;

/**
 *  <code>popframes001a</code> is deugee's part of the popframes001.
 */

public class popframes001a {

    volatile public static boolean finishIt = false;

    public static void main(String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.out, argHandler);

        popframes001b.loadClass = true;

        while (!finishIt) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }

        log.display("completed succesfully.");
        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }
}

class popframes001b {
    public final static int INITIAL_VALUE        = 0;

    public static boolean loadClass = false;
    public static int flag = INITIAL_VALUE;

    public final static String methodName = "runIt";
    public final static String methodNameCaller = "runItCaller";
    public final static String flagName = "flag";

    public static void runIt() {
        flag = INITIAL_VALUE;
    }

    // We need to call runIt() from a java function.
    // This is because jvmti function popFrame() requires that
    // both calling and called methods are java functions.
    public static void runItCaller() {
        runIt();
    }
}
