/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.IterateOverInstancesOfClass;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class iterinstcls003 extends DebugeeClass {

    /** Load native library if required.*/
    static {
        loadLibrary("iterinstcls003");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new iterinstcls003().runIt(argv, out);
    }

    /* =================================================================== */

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    /* constants */
    public static final int DEFAULT_CHAIN_LENGTH = 4;

    /** Tested object. */
    public static iterinstcls003RootTestedClass object = null;

    /** Run debugee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        int chainLength = argHandler.findOptionIntValue("objects", DEFAULT_CHAIN_LENGTH);

        log.display("Creating chain of tested objects: " + chainLength + " length");
        object = new iterinstcls003RootTestedClass(chainLength);

        log.display("Sync: objects created");
        status = checkStatus(status);

        log.display("Cleaning links to unreachable objects");
        object.cleanUnreachable();

        log.display("Sync: objects are unreachable");
        status = checkStatus(status);

        return status;
    }
}

/* =================================================================== */

/** Class for root tested object. */
class iterinstcls003RootTestedClass {
    int length;

    iterinstcls003TestedClass reachableChain = null;
    iterinstcls003TestedClass unreachableChain = null;

    public iterinstcls003RootTestedClass(int length) {
        this.length = length;
        reachableChain = new iterinstcls003TestedClass(length);
        unreachableChain = new iterinstcls003TestedClass(length);
    }

    public void cleanUnreachable() {
        unreachableChain = null;
    }
}

/** Class for tested chain object. */
class iterinstcls003TestedClass {
    int level;

    iterinstcls003TestedClass tail = null;

    public iterinstcls003TestedClass(int length) {
        this.level = length;
        if (length > 1) {
            tail = new iterinstcls003TestedClass(length - 1);
        }
    }
}
