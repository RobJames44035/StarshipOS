/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.ForceGarbageCollection;

import java.io.PrintStream;
import java.lang.ref.SoftReference;

import nsk.share.*;
import nsk.share.jvmti.*;

public class forcegc002 extends DebugeeClass {

    /** Load native library if required. */
    static {
        loadLibrary("forcegc002");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new forcegc002().runIt(argv, out);
    }

    /* =================================================================== */

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    /* constants */
    public static final int DEFAULT_OBJECTS_COUNT = 100;

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        int count = argHandler.findOptionIntValue("objects", DEFAULT_OBJECTS_COUNT);

        log.display("Creating tested objects: " + count + " objects");
        Object objects[] = new Object[count];
        for (int i = 0; i < count; i++) {
            objects[i] = new Object();
        }

        log.display("Clearing references to the tested objects");
        objects = null;

        log.display("Sync: objects ready for GC");
        status = checkStatus(status);

        return status;
    }
}
