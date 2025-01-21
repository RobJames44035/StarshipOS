/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.Event.CLASS_UNLOAD;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class clsunload001a {

    public static void main(String args[]) {
        clsunload001a _clsunload001a = new clsunload001a();
        System.exit(clsunload001.JCK_STATUS_BASE + _clsunload001a.runIt(args, System.err));
//        _clsunload001a.runIt(args, System.err);
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
        return clsunload001.PASSED;
    }

    // tested class
    public static class TestedClass {
        int foo = 0;

        public TestedClass() {
            foo = 1000;
        }
    }

}
