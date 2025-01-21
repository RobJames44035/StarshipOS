/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdwp.ClassType.NewInstance;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class newinst001a {

    // name of the tested thread
    public static final String THREAD_NAME = "testedThread";

    // line nunber for breakpoint
    public static final int BREAKPOINT_LINE_NUMBER = 88;

    // initial and final value of variable changed by the method invoked from debugger
    public static final int INITIAL_VALUE = 10;
    public static final int FINAL_VALUE = 1234;

    // scaffold objects
    private static volatile ArgumentHandler argumentHandler = null;
    private static volatile Log log = null;

    public static void main(String args[]) {
        newinst001a _newinst001a = new newinst001a();
        System.exit(newinst001.JCK_STATUS_BASE + _newinst001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        argumentHandler = new ArgumentHandler(args);
        log = new Log(out, argumentHandler);

        // create tested of tested class
        log.display("Accessing to tested class");
        TestedObjectClass.foo = 100;
        log.display("  ... class loaded");

        // invoke method with breakpoint
        TestedObjectClass.run();

        log.display("Debugee PASSED");
        return newinst001.PASSED;
    }

    // tested object class
    public static class TestedObjectClass {
        public static int foo = 0;

        // result of invoking tested mathod
        public static volatile int result = INITIAL_VALUE;

        // suspend current thread on on breakpoint
        public static void run() {
            log.display("Tested thread: started");

            log.display("Breakpoint line reached");
            // next line is for breakpoint
            int foo = 0; // BREAKPOINT_LINE_NUMBER
            log.display("Breakpoint line passed");

            log.display("Tested thread: finished");
        }

        // tested constructor for invokation from debugger
        public TestedObjectClass(int arg) {
            log.display("Tested constructor invoked with argument:" + arg);
            if (arg != FINAL_VALUE) {
                log.complain("Unexpected value of constructor argument: " + arg
                            + " (expected: " + FINAL_VALUE + ")");
            }
            result = arg;
        }
    }
}
