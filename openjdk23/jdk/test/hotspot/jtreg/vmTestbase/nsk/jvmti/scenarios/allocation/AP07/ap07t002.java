/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.allocation.AP07;

import java.io.*;
import java.lang.reflect.*;

import nsk.share.*;
import nsk.share.jvmti.*;

public class ap07t002 extends DebugeeClass {
    /* number of interations to provoke garbage collecting */
    final static long IGNORE_TAG = 10l;

    public static void main(String[] argv) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // produce JCK-like exit status
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream out) {
        return new ap07t002().runThis(argv, out);
    }

    private ap07t002 right;
    private ap07t002 left;

    private native void setTag(ap07t002 target, long tag);

    /* scaffold objects */
    static ArgumentHandler argHandler = null;
    static Log log = null;
    static long timeout = 0;
    int status = Consts.TEST_PASSED;

    private int runThis(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        // build right branch
        ap07t002 rightLoc = new ap07t002();
        rightLoc.left     = new ap07t002();
        rightLoc.right    = new ap07t002();
        setTag(rightLoc,       IGNORE_TAG);
        setTag(rightLoc.left,  IGNORE_TAG + 1l);
        setTag(rightLoc.right, IGNORE_TAG + 2l);

        // build left branch
        ap07t002 leftLoc  = new ap07t002();
        leftLoc.left      = new ap07t002();
        leftLoc.right     = new ap07t002();
        setTag(leftLoc,       1l);
        setTag(leftLoc.left,  2l);
        setTag(leftLoc.right, 3l);

        status = checkStatus(status);
        return status;
    }
}
