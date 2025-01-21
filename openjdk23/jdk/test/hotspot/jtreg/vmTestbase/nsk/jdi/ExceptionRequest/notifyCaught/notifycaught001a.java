/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ExceptionRequest.notifyCaught;

import nsk.share.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the notifycaught001 JDI test.
 */

public class notifycaught001a {

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
                            thread1 = JDIThreadFactory.newThread(new Thread1notifycaught001a("thread1"));
                            log1("new notifycaught001a().run1(thread1);");
                            new notifycaught001a().run1(thread1);

    //-------------------------------------------------    standard end section

                    default:
                                instruction = end;
                                break;
                }
                log1("methodForCommunication();");
                methodForCommunication();
                if (instruction == end)
                    break;
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


class TestClass10{
    void m10() {
        throw new NullPointerException("m10");
    }
}
class TestClass11 extends TestClass10{
    void m11() {

        try {
            (new TestClass10()).m10();
        } catch ( NullPointerException e ) {
        }
        throw new NullPointerException("m11");
    }
}

class Thread1notifycaught001a extends NamedTask {

    public Thread1notifycaught001a(String threadName) {
        super(threadName);
    }

    public void run() {
        notifycaught001a.log1("  'run': enter  :: threadName == " + getName());
        try {
            (new TestClass11()).m11();
        } catch ( NullPointerException e) {
        }
        notifycaught001a.log1("  'run': exit   :: threadName == " + getName());
        return;
    }
}
