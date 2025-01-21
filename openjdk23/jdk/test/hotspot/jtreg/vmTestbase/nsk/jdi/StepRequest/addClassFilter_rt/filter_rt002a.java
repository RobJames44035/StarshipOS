/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.StepRequest.addClassFilter_rt;

import nsk.share.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the filter_rt002 JDI test.
 */

public class filter_rt002a {

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

    static filter_rt002aTestClass10 obj = new filter_rt002aTestClass10();

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

        label0:
            for (int i = 0; ; i++) {

                if (instruction > maxInstr) {
                    logErr("ERROR: unexpected instruction: " + instruction);
                    exitCode = FAILED;
                    break ;
                }

                switch (i) {

    //------------------------------------------------------  section tested

                    case 0:
                            thread1 = JDIThreadFactory.newThread(new Thread1filter_rt002a("thread1"));

                            synchronized (lockObj) {
                                threadStart(thread1);
                                log1("methodForCommunication();----");
                                methodForCommunication();
                            }

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

class filter_rt002aTestClass10{
    static void m10() {
        filter_rt002a.log1("entered: m10");
    }
}
class filter_rt002aTestClass11 extends filter_rt002aTestClass10{
    static void m11() {
        filter_rt002a.log1("entered: m11");
        filter_rt002aTestClass10.m10();
    }
}

class Thread1filter_rt002a extends NamedTask {

    public Thread1filter_rt002a(String threadName) {
        super(threadName);
    }

    public void run() {
        filter_rt002a.log1("  'run': enter  :: threadName == " + getName());
        synchronized(filter_rt002a.waitnotifyObj) {
            filter_rt002a.waitnotifyObj.notify();
        }
        synchronized(filter_rt002a.lockObj) {
            filter_rt002aTestClass11.m11();
        }
        filter_rt002a.log1("  'run': exit   :: threadName == " + getName());
        return;
    }
}
