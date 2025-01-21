/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventRequest.setEnabled;

import nsk.share.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the setenabled003 JDI test.
 */

public class setenabled003a {

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
                            thread1 = JDIThreadFactory.newThread(new Thread1setenabled003a("thread1"));
                            Thread1setenabled003a.method();
                            break;

                    case 1:
                            synchronized (lockObj) {
                                threadStart(thread1);
                                log1("methodForCommunication();----");
                                methodForCommunication();
                            }
//                  case 2:
                            try {
                                thread1.join();
                                log1("methodForCommunication();----2");
                                methodForCommunication();
                            } catch ( InterruptedException e ) {
                            }
                            i++;


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

class Thread1setenabled003a extends NamedTask {

    public Thread1setenabled003a(String threadName) {
        super(threadName);
    }

    public void run() {
        setenabled003a.log1("  'run': enter  :: threadName == " + getName());
        synchronized(setenabled003a.waitnotifyObj) {
            setenabled003a.waitnotifyObj.notify();
        }

        synchronized(setenabled003a.lockObj) {
            setenabled003a.log1("  'run': exit   :: threadName == " + getName());
        }
        return;
    }

   static void method() {
       setenabled003a.log1("  enetred: method");
   }

}
