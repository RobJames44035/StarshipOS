/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdwp.Event.THREAD_DEATH;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class thrdeath001a {

    static final int BREAKPOINT_LINE = 93;

    static ArgumentHandler argumentHandler = null;
    static Log log = null;

    public static void main(String args[]) {
        thrdeath001a _thrdeath001a = new thrdeath001a();
        System.exit(thrdeath001.JCK_STATUS_BASE + _thrdeath001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        argumentHandler = new ArgumentHandler(args);
        log = new Log(out, argumentHandler);

        // create tested thread
        log.display("Creating tested thread");
        TestedClass.thread = new TestedClass(thrdeath001.TESTED_THREAD_NAME);
        log.display("  ... thread created");

        // reach breakpoint
        TestedClass.ready();

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
            return thrdeath001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return thrdeath001.PASSED;
    }

    // tested class
    public static class TestedClass extends Thread {
        public static volatile TestedClass thread = null;

        public TestedClass(String name) {
            super(name);
        }

        public static void ready() {
            log.display("Breakpoint line reached");
            // next line is for breakpoint
            int foo = 0; // BREAKPOINT_LINE
            log.display("Breakpoint line passed");
        }

        public void run() {
            log.display("Tested thread: started");
            log.display("Tested thread: finished");
        }
    }

}
