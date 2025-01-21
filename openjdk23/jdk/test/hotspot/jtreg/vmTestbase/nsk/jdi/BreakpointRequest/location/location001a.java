/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.BreakpointRequest.location;

import nsk.share.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the location001 JDI test.
 */

public class location001a {

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

    static Thread thread1 = null;

    static TestClass objTC = new TestClass();

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
                            thread1 = JDIThreadFactory.newThread(new Threadlocation001a("thread1"));
                            break;

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

    static Object lockingObj[] = new Object[2];
    static volatile int number = 0;

    static class Threadlocation001a extends NamedTask {

        int tNumber;

        public Threadlocation001a(String threadName) {
            super(threadName);
            tNumber = number;
            number++;
            lockingObj[tNumber] = threadName;
        }

        public void run() {
            log1("  'run': enter  :: threadName == " + getName());
            if (lockingObj[tNumber] == null)
                log1("lockingObj[tNumber] == null");
            synchronized(lockingObj[tNumber]) {
                synchronized (waitnotifyObj) {
                    waitnotifyObj.notify();
                }
                objTC.method();
            }
            log1("  'run': exit   :: threadName == " + getName());
            return;
        }
    }

}

class TestClass {

    static int breakpointLine = 3;
    static String awFieldName = "var1";
    static String mwFieldName = "var2";

    static int var1 = 0;
    static int var2 = 0;
    static int var3 = 0;

    static void method () {
        var1 = 1;
        var3 = var1;
        var2 = var3;
    }
}
