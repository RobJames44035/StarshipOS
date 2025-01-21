/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdwp.Event.FIELD_ACCESS;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class fldaccess001a {

    static final int BREAKPOINT_LINE = 114;
    static final int FIELD_ACCESS_LINE = 125;

    static ArgumentHandler argumentHandler = null;
    static Log log = null;

    public static void main(String args[]) {
        fldaccess001a _fldaccess001a = new fldaccess001a();
        System.exit(fldaccess001.JCK_STATUS_BASE + _fldaccess001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        argumentHandler = new ArgumentHandler(args);
        log = new Log(out, argumentHandler);

        // create tested thread and object
        log.display("Creating object of the tested class");
        TestedObjectClass.object = new TestedObjectClass();
        log.display("  ... object created");

        log.display("Creating tested thread");
        TestedThreadClass thread = new TestedThreadClass(fldaccess001.TESTED_THREAD_NAME);
        log.display("  ... thread created");

        // start tested thread
        log.display("Starting tested thread");
        thread.start();
        log.display("  ... thread started");

        // wait for thread finished
        try {
            log.display("Waiting for tested thread finished");
            thread.join();
            log.display("  ... thread finished");
        } catch (InterruptedException e) {
            log.complain("Interruption while waiting for tested thread finished");
            return fldaccess001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return fldaccess001.PASSED;
    }

    // tested thread class
    public static class TestedThreadClass extends Thread {

        public TestedThreadClass(String name) {
            super(name);
        }

        public void run() {
            log.display("Tested thread: started");

            // invoke method of tested object class
            TestedObjectClass.run();

            log.display("Tested thread: finished");
        }
    }

    // tested object class
    public static class TestedObjectClass {

        // static field with object been accessed
        public static volatile TestedObjectClass object = null;

        // static field been accessed
        public static int value = 0;

        // reach breakpoint and then touch field
        public static void run() {
            log.display("Breakpoint line reached");
            // next line is location of BREAKPOINT event
            int foo = 0; // BREAKPOINT_LINE
            log.display("Breakpoint line passed");

            // invoke method which accesses the field
            methodForAccess();
        }

        // access the tested field
        public static void methodForAccess() {
            log.display("Before tested field accessed");
            // next line is location of FIELD_ACCESS event
            int foo = value; // FIELD_ACCESS_LINE
            log.display("After tested field accessed");
        }
    }

}
