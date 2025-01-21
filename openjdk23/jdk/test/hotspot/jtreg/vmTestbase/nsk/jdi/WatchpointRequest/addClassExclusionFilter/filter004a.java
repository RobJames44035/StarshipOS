/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.WatchpointRequest.addClassExclusionFilter;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the filter004 JDI test.
 */

public class filter004a {

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

    static filter004aTestClass10 obj10 = new filter004aTestClass10();
    static filter004aTestClass11 obj11 = new filter004aTestClass11();

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
//                            break;

    //-------------------------------------------------    standard end section

                    default:
                                instruction = end;
                                break;
                }
            }

        log1("debuggee exits");
        System.exit(exitCode + PASS_BASE);
    }
/*
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
*/
}

class filter004aTestClass10 {
    static int var101 = 0;
    static int var102 = 0;
    static int var103 = 0;

    static void method () {
        var101 = 1;
        var103 = var101;
        var102 = var103;
    }
}
class filter004aTestClass11 extends filter004aTestClass10 {

    static int var111 = 0;
    static int var112 = 0;
    static int var113 = 0;

    static void method () {
        var101 = 1;
        var103 = var101;
        var102 = var103;

        var111 = 1;
        var113 = var111;
        var112 = var113;
//        filter004aTestClass10.method();
    }
}
