/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ExceptionRequest.addClassFilter_s;

import nsk.share.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the filter_s001 JDI test.
 */

public class filter_s001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    static ArgumentHandler argHandler;
    static Log log;

    //--------------------------------------------------   log procedures

    public static void log1(String message) {
        log.display("**> debuggee: " + message);
    }

    private static void logErr(String message) {
        log.complain("**> debuggee: " + message);
    }

    //====================================================== test program

    static Thread thread1 = null;
    static Thread thread2 = null;

    //------------------------------------------------------ common section

    static int exitCode = PASSED;

    static int instruction = 1;
    static int end         = 0;
                                   //    static int quit        = 0;
                                   //    static int continue    = 2;
    static int maxInstr    = 1;    // 2;

    static int lineForComm = 2;

    private static void methodForCommunication() {
        int i1 = instruction;
        int i2 = i1;
        int i3 = i2;
    }
    //----------------------------------------------------   main method

    public static void main (String argv[]) {

        argHandler = new ArgumentHandler(argv);
        log = argHandler.createDebugeeLog();

        log1("debuggee started!");


            for (int i = 0; ; i++) {

                log1("methodForCommunication();");
                methodForCommunication();
                if (instruction == end)
                    break;

                if (instruction > maxInstr) {
                    logErr("ERROR: unexpected instruction: " + instruction);
                    exitCode = FAILED;
                    break ;
                }

                switch (i) {
    //------------------------------------------------------  section tested

                    case 0:
                    thread1 = JDIThreadFactory.newThread(new filter_s001aThread1("thread1"));
                    log1("new filter_s001a().run1(thread1);");
                    new filter_s001a().run1(thread1);

                    break;

                    case 1:
                    thread2 = JDIThreadFactory.newThread(new filter_s001aThread2("thread2"));
                    log1("new filter_s001a().run1(thread2);");
                    new filter_s001a().run1(thread2);

    //-------------------------------------------------    standard end section

                    default:
                    instruction = end;
                    break;
                }
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
            } catch ( Exception e) {
                exitCode = FAILED;
                logErr("       Exception : " + e );
                return FAILED;
            }
        }
        return PASSED;
    }

    public void run1(Thread t) {
        t.start();
        try {
            t.join();
        } catch ( InterruptedException e ) {
        }
    }
}


class filter_s001aTestClass10{
    void m10() {
        throw new NullPointerException("m10");
    }
}
class filter_s001aTestClass11 extends filter_s001aTestClass10{
    void m11() {

        try {
            (new filter_s001aTestClass10()).m10();
        } catch ( NullPointerException e ) {
        }
        throw new NullPointerException("m11");
    }
}

class filter_s001aThread1 extends NamedTask {

    public filter_s001aThread1(String threadName) {
        super(threadName);
    }

    public void run() {
        filter_s001a.log1("  'run': enter  :: threadName == " + getName());
        try {
            (new filter_s001aTestClass11()).m11();
        } catch ( NullPointerException e) {
        }
        filter_s001a.log1("  'run': exit   :: threadName == " + getName());
        return;
    }
}

class filter_s001aThread2 extends NamedTask {

    class filter_s001aTestClass20{
        void m20() {
            throw new NullPointerException("m20");
        }
    }
    class filter_s001aTestClass21 extends filter_s001aTestClass20{
        void m21() {

            try {
                (new filter_s001aTestClass20()).m20();
            } catch ( NullPointerException e ) {
            }
            throw new NullPointerException("m11");
        }
    }

    public filter_s001aThread2(String threadName) {
        super(threadName);
    }

    public void run() {
        filter_s001a.log1("  'run': enter  :: threadName == " + getName());
        try {
            (new filter_s001aTestClass21()).m21();
        } catch ( NullPointerException e) {
        }
        filter_s001a.log1("  'run': exit   :: threadName == " + getName());
        return;
    }
}
