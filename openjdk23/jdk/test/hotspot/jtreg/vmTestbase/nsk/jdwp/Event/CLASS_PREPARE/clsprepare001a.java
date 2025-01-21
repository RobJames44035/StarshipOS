/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.Event.CLASS_PREPARE;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class clsprepare001a {

    public static void main(String args[]) {
        clsprepare001a _clsprepare001a = new clsprepare001a();
        System.exit(clsprepare001.JCK_STATUS_BASE + _clsprepare001a.runIt(args, System.err));
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
        return clsprepare001.PASSED;
    }

    // tested class
    public static class TestedClass {
        int foo = 0;

        public TestedClass() {
            foo = 1000;
        }
    }

}
