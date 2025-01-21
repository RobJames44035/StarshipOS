/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetObjectSize;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class objsize001 extends DebugeeClass {

    /** Load native library if required. */
    static {
        loadLibrary("objsize001");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new objsize001().runIt(argv, out);
    }

    /* =================================================================== */

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    /** Tested object. */
    public static objsize001TestedClass testedObject = null;

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        log.display("Creating tested object");
        testedObject = new objsize001TestedClass();

        log.display("Sync: object created");
        status = checkStatus(status);

        log.display("Changing object data");
        testedObject.change();

        log.display("Sync: object data changed");
        status = checkStatus(status);

        return status;
    }
}

/* =================================================================== */

/** Class for tested object. */
class objsize001TestedClass {

    // static data
    public static int staticIntValue = 100;
    public static long staticLongValue = 100;
    public static double staticDoubleValue = 100.001;
    public static String staticStringValue = "hundred";

    // public object data
    public int publicIntValue = 100;
    public long publicLongValue = 100;
    public double publicDoubleValue = 100.002;
    public String publicStringValue = "hundred";

    // private object data
    private int privateIntValue = 100;
    private long privateLongValue = 100;
    private double privateDoubleValue = 100.001;
    private String privateStringValue = "hundred";

    /** Change all object data. */
    public void change() {

        // change static data
        staticIntValue = -200;
        staticLongValue = 200;
        staticDoubleValue = -200.002e-2;
        staticStringValue = "changed";

        // change public object data
        publicIntValue = 300;
        publicLongValue = -300;
        publicDoubleValue = 300.003e+3;
        publicStringValue = "changed too";

        // change private object data
        privateIntValue = -400;
        privateLongValue = 400;
        privateDoubleValue = -400.004e-4;
        privateStringValue = null;

    }
}
