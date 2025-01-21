/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.StepRequest.addClassFilter_s;

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

                if (instruction > maxInstr) {
                    logErr("ERROR: unexpected instruction: " + instruction);
                    exitCode = FAILED;
                    break ;
                }

                switch (i) {

    //------------------------------------------------------  section tested

                    case 0:
                            thread1 = JDIThreadFactory.newThread(new Thread1filter_s001a("thread1"));

                            synchronized (lockObj) {
                                threadStart(thread1);
                                log1("methodForCommunication();----1");
                                methodForCommunication();
                            }
                                try {
                                    thread1.join();
                                } catch ( InterruptedException e ) {
                                }
                            break;


                    case 1:
                            thread2 = JDIThreadFactory.newThread(new Thread2filter_s001a("thread2"));

                            synchronized (lockObj) {
                                threadStart(thread2);
                                log1("methodForCommunication();----2");
                                methodForCommunication();
                            }
                                try {
                                    thread2.join();
                                } catch ( InterruptedException e ) {
                                }
    //-------------------------------------------------    standard end section

                    default:
                                instruction = end;
                                break;
                }
                log1("methodForCommunication();");
//                methodForCommunication();
                if (instruction == end)
                    break;
            }

        log1("debuggee exits");
        System.exit(exitCode + PASS_BASE);
    }

    static Object lockObj       = new Object();
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
}

class TestClass10{
    static void m10() {
        filter_s001a.log1("entered: m10");
    }
}
class TestClass11 extends TestClass10{
    static void m11() {
        filter_s001a.log1("entered: m11");
        TestClass10.m10();
    }
}

class Thread1filter_s001a extends NamedTask {

    public Thread1filter_s001a(String threadName) {
        super(threadName);
    }

    public void run() {
        filter_s001a.log1("  'run': enter  :: threadName == " + getName());
        synchronized(filter_s001a.waitnotifyObj) {
            filter_s001a.waitnotifyObj.notify();
        }
        synchronized(filter_s001a.lockObj) {
            TestClass11.m11();
        }
        filter_s001a.log1("  'run': exit   :: threadName == " + getName());
        return;
    }
}

class Thread2filter_s001a extends NamedTask {

    static class TestClass20{
        static void m20() {
            filter_s001a.log1("entered: m20");
        }
    }
    static class TestClass21 extends TestClass20{
        static void m21() {
            filter_s001a.log1("entered: m20");
            TestClass20.m20();
        }
    }


    public Thread2filter_s001a(String threadName) {
        super(threadName);
    }

    public void run() {
        filter_s001a.log1("  'run': enter  :: threadName == " + getName());
        synchronized(filter_s001a.waitnotifyObj) {
            filter_s001a.waitnotifyObj.notify();
        }
        synchronized(filter_s001a.lockObj) {
            TestClass21.m21();
        }
        filter_s001a.log1("  'run': exit   :: threadName == " + getName());
        return;
    }
}
