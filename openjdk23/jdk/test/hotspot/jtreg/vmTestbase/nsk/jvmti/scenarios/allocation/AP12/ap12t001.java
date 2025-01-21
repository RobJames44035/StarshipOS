/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.allocation.AP12;

import java.io.*;
import java.lang.ref.*;

import nsk.share.*;
import nsk.share.jvmti.*;

public class ap12t001 extends DebugeeClass {
    /* number of interations to provoke garbage collecting */
    final static int GC_TRYS = 1;

    public static void main(String[] argv) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // produce JCK-like exit status
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream out) {
        return new ap12t001().runThis(argv, out);
    }

    private native void setTag(long tag);
    private native void flushObjectFreeEvents();

    private static ap12t001[] ap12t001arr =  { new ap12t001(), new ap12t001() };

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    private int runThis(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        SoftReference<ap12t001> softRef = new SoftReference<ap12t001> (ap12t001arr[0]);
        WeakReference<ap12t001> weakRef = new WeakReference<ap12t001> (ap12t001arr[1]);
        ap12t001arr[0].setTag(1L);
        ap12t001arr[1].setTag(2L);
        // nullify references to ap12t001 objects to make them collectable
        ap12t001arr = null;

        log.display("References are ready for garbage collection");
        status = checkStatus(status);

        // Provoke OutOfMemoryError in order to clear all soft references
        for (int i= 0; i < GC_TRYS; i++)
            ClassUnloader.eatMemory();
        log.display("GC called");
        flushObjectFreeEvents();

        status = checkStatus(status);
        return status;
    }
}
