/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventSet.eventIterator;

import nsk.share.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the eventiterator003 JDI test.
 */

public class eventiterator003a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;

    static final int FAILED = 2;

    static final int PASS_BASE = 95;

    static ArgumentHandler argHandler;

    static Log log;

    //--------------------------------------------------   log procedures

    private static void log1(String message) {
        log.display("**> debuggee: " + message);
    }

    private static void logErr(String message) {
        log.complain("**> debuggee: " + message);
    }

    //====================================================== test program

    static Thread thread2 = null;

    //------------------------------------------------------ common section

    static int exitCode = PASSED;

    static int instruction = 1;

    static int end = 0;

    static int maxInstr = 1; // 2;

    static int lineForComm = 2;

    private static void methodForCommunication() {
        int i1 = instruction;
        int i2 = i1;
        int i3 = i2;
    }

    //----------------------------------------------------   main method

    public static void main(String argv[]) {

        argHandler = new ArgumentHandler(argv);
        log = argHandler.createDebugeeLog();

        log1("debuggee started!");
        thread2 = JDIThreadFactory.newThread(new Thread2eventiterator003a("thread2"));
        methodForCommunication();

        threadStart(thread2);
        try {
            thread2.join();
        } catch (InterruptedException e) {
        }
        log1("debuggee exits");
        System.exit(exitCode + PASS_BASE);
    }

    static Object waitnotifyObj = new Object();

    static int threadStart(Thread t) {
        synchronized (waitnotifyObj) {
            t.start();
            try {
                waitnotifyObj.wait();
            } catch (Exception e) {
                exitCode = FAILED;
                logErr("       Exception : " + e);
                return FAILED;
            }
        }
        return PASSED;
    }

    static class Thread2eventiterator003a extends NamedTask {

        public Thread2eventiterator003a(String threadName) {
            super(threadName);
        }

        public void run() {
            synchronized (waitnotifyObj) {
                waitnotifyObj.notify();
            }
            return;
        }
    }

}
