/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdwp.Event.BREAKPOINT;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class breakpoint001a {

    static final int BREAKPOINT_LINE = 91;

    static ArgumentHandler argumentHandler = null;
    static Log log = null;

    public static void main(String args[]) {
        breakpoint001a _breakpoint001a = new breakpoint001a();
        System.exit(breakpoint001.JCK_STATUS_BASE + _breakpoint001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        argumentHandler = new ArgumentHandler(args);
        log = new Log(out, argumentHandler);

        // create tested thread
        log.display("Creating tested thread");
        TestedClass.thread = new TestedClass(breakpoint001.TESTED_THREAD_NAME);
        log.display("  ... thread created");

        // start tested thread
        log.display("Starting tested thread");
        TestedClass.thread.start();
        log.display("  ... thread started");

        // wait for thread finished
        try {
            log.display("Waiting for tested thread finished");
            TestedClass.thread.join();
            log.display("  ... thread finished");
        } catch (InterruptedException e) {
            log.complain("Interruption while waiting for tested thread finished");
            return breakpoint001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return breakpoint001.PASSED;
    }

    // tested class
    public static class TestedClass extends Thread {
        public static volatile TestedClass thread = null;

        public TestedClass(String name) {
            super(name);
        }

        public void run() {
            log.display("Tested thread: started");
            log.display("Breakpoint line reached");
            // next line is for breakpoint
            int foo = 0; // BREAKPOINT_LINE
            log.display("Breakpoint line passed");
            log.display("Tested thread: finished");
        }
    }

}
