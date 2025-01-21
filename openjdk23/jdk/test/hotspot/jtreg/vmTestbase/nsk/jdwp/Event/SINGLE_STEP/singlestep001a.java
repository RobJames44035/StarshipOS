/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdwp.Event.SINGLE_STEP;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class singlestep001a {

    static final int BREAKPOINT_LINE = 91;
    static final int SINGLE_STEP_LINE = 94;

    static ArgumentHandler argumentHandler = null;
    static Log log = null;

    public static void main(String args[]) {
        singlestep001a _singlestep001a = new singlestep001a();
        System.exit(singlestep001.JCK_STATUS_BASE + _singlestep001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        argumentHandler = new ArgumentHandler(args);
        log = new Log(out, argumentHandler);

        // create tested thread
        log.display("Creating tested thread");
        TestedClass thread = new TestedClass(singlestep001.TESTED_THREAD_NAME);
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
            return singlestep001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return singlestep001.PASSED;
    }

    // tested class
    public static class TestedClass extends Thread {
        public TestedClass(String name) {
            super(name);
        }

        public void run() {
            log.display("Tested thread: started");

            log.display("Breakpoint line reached");
            // next line is for breakpoint
            int foo = 0; // BREAKPOINT_LINE

            // next line is for SINGLE_STEP event
            foo = 10; // SINGLE_STEP_LINE

            log.display("Tested thread: finished");
        }
    }
}
