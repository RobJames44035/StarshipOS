/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.MethodEntryRequest.addClassFilter_rt;

import nsk.share.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the filter_rt003 JDI test.
 */

public class filter_rt003a {

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

        filter_rt003aTestClass11 obj1 = new filter_rt003aTestClass11();
        filter_rt003aTestClass21 obj2 = new filter_rt003aTestClass21();

        thread1 = JDIThreadFactory.newThread(new filter_rt003aThread1("thread1"));
        thread2 = JDIThreadFactory.newThread(new filter_rt003aThread2("thread2"));

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
                log1("new filter_rt003a().run1(thread1);");
                new filter_rt003a().run1(thread1);

                log1("new filter_rt003a().run1(thread2);");
                new filter_rt003a().run1(thread2);

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


class filter_rt003aTestClass10{
    static void m10() {
        filter_rt003a.log1("entered: m10()");
    }
}
class filter_rt003aTestClass11 extends filter_rt003aTestClass10{
    static void m11() {
        filter_rt003a.log1("entered: m11()");
        filter_rt003aTestClass10.m10();
    }
}

class filter_rt003aThread1 extends NamedTask {

    public filter_rt003aThread1(String threadName) {
        super(threadName);
    }

    public void run() {
        filter_rt003a.log1("  'run': enter  :: threadName == " + getName());
        filter_rt003aTestClass11.m11();
        filter_rt003aTestClass21.m21();
        filter_rt003a.log1("  'run': exit   :: threadName == " + getName());
        return;
    }
}

class filter_rt003aTestClass20{
    static void m20() {
        filter_rt003a.log1("entered: m20()");
    }
}
class filter_rt003aTestClass21 extends filter_rt003aTestClass20{
    static void m21() {
        filter_rt003a.log1("entered: m21()");
        filter_rt003aTestClass20.m20();
    }
}

class filter_rt003aThread2 extends NamedTask {

    public filter_rt003aThread2(String threadName) {
        super(threadName);
    }

    public void run() {
        filter_rt003a.log1("  'run': enter  :: threadName == " + getName());
        filter_rt003aTestClass21.m21();
        filter_rt003a.log1("  'run': exit   :: threadName == " + getName());
        return;
    }
}
