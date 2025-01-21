/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventSet.resume;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the resume010 JDI test.
 */

public class resume010a {

    //----------------------------------------------------- template section

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

    static resume010aTestClass tcObject = new resume010aTestClass();

    //------------------------------------------------------ common section

    static int exitCode = PASSED;

    static int testCase    = -1;
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

                log1("methodForCommunication(); : " + i);
                methodForCommunication();

                switch (i) {

    //------------------------------------------------------  section tested

                    case 0:
                        resume010aTestClass.method();
                        // Wait for debugger to complete the first test case
                        // before advancing to the first breakpoint
                        waitForTestCase(0);
                        break;

                    case 1: resume010aTestClass.method();
                            break;

                    case 2: resume010aTestClass.method();

    //-------------------------------------------------    standard end section

                    default:
                                instruction = end;
                                methodForCommunication();
                                break label0;
                }
            }

        System.exit(exitCode + PASS_BASE);
    }
    // Synchronize with debugger progression.
    static void waitForTestCase(int t) {
        while (testCase < t) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignored
            }
        }
    }
}
class resume010aTestClass {

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
