/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.allocation.AP07;

import java.io.*;
import java.lang.reflect.*;

import nsk.share.*;
import nsk.share.jvmti.*;

public class ap07t001 extends DebugeeClass {
    /* number of interations to provoke garbage collecting */
    final static long IGNORE_TAG = 10l;

    public static void main(String[] argv) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // produce JCK-like exit status
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream out) {
        return new ap07t001().runThis(argv, out);
    }

    private ap07t001 right;
    private ap07t001 left;

    private native void setTag(ap07t001 target, long tag);
    private native void setRoot();

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
        right       = new ap07t001();
        right.left  = new ap07t001();
        right.right = new ap07t001();
        setTag(right,       IGNORE_TAG);
        setTag(right.left,  IGNORE_TAG + 1l);
        setTag(right.right, IGNORE_TAG + 2l);

        // build left branch
        left       = new ap07t001();
        left.left  = new ap07t001();
        left.right = new ap07t001();
        setTag(left,       1l);
        setTag(left.left,  2l);
        setTag(left.right, 3l);

        setRoot();

        status = checkStatus(status);
        return status;
    }
}
