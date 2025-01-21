/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.EventRequest.ClearAllBreakpoints;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class clrallbreakp002a {

    public static void main(String args[]) {
        clrallbreakp002a _clrallbreakp002a = new clrallbreakp002a();
        System.exit(clrallbreakp002.JCK_STATUS_BASE + _clrallbreakp002a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        // ensure tested class is loaded
        log.display("Creating object of tested class");
        TestedClass foo = new TestedClass();
        log.display("  ... object created");

        // exit debugee
        log.display("Debugee PASSED");
        return clrallbreakp002.PASSED;
    }

    // tested class
    public static class TestedClass {
        int foo = 0;

        public TestedClass() {
            foo = 1000;
        }
    }
}
