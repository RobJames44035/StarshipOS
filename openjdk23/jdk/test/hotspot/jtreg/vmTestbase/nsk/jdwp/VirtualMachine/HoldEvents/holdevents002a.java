/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdwp.VirtualMachine.HoldEvents;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class holdevents002a {

    static final int BREAKPOINT_LINE = 80;

    static ArgumentHandler argumentHandler = null;
    static Log log = null;

    public static void main(String args[]) {
        holdevents002a _holdevents002a = new holdevents002a();
        System.exit(holdevents002.JCK_STATUS_BASE + _holdevents002a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        argumentHandler = new ArgumentHandler(args);
        log = new Log(out, argumentHandler);

        // ensure tested class is loaded
        log.display("Creating object of tested class");
        TestedClass object = new TestedClass();
        log.display("  ... object created");

        // invoke method with breakpoint
        log.display("Invoking method with breakpoint");
        object.run();
        log.display("  ... method invoked");

        // exit debugee
        log.display("Debugee PASSED");
        return holdevents002.PASSED;
    }

    // tested class
    public static class TestedClass {
        int foo = 0;

        public TestedClass() {
            foo = 1000;
        }

        public void run() {
            log.display("Breakpoint line reached");
            // next line is for breakpoint
            foo = 0; // BREAKPOINT_LINE
            log.display("Breakpoint line passed");
        }
    }
}
