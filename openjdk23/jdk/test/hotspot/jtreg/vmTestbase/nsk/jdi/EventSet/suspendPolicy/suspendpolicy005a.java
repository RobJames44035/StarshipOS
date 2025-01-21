/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventSet.suspendPolicy;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the suspendpolicy005 JDI test.
 */

public class suspendpolicy005a {

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

    static suspendpolicy005aTestClass tcObject = new suspendpolicy005aTestClass();

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

        int exitCode = PASSED;


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

                    case 0: suspendpolicy005aTestClass.method();
                            break;

                    case 1: suspendpolicy005aTestClass.method();
                            break;

                    case 2: suspendpolicy005aTestClass.method();
                            break;

                    case 3: suspendpolicy005aTestClass.method();
                            break;

                    case 4: suspendpolicy005aTestClass.method();
                            break;

                    case 5: suspendpolicy005aTestClass.method();
                            break;

                    case 6: suspendpolicy005aTestClass.method();

    //-------------------------------------------------    standard end section

                    default:
                                instruction = end;
                                methodForCommunication();
                                break label0;
                }
            }

        System.exit(exitCode + PASS_BASE);
    }

    public static void nullMethod() {
        throw new NullPointerException("test");
    }

}

class suspendpolicy005aTestClass {

    static int breakpointLine = 3;
    static String awFieldName = "var1";
    static String mwFieldName = "var2";

    static int var1 = 0;
    static int var2 = 0;
    static int var3 = 0;

    static void method () {
        var1 += 1;
        var3 += 1;
        var2  = var3;
        try {
            suspendpolicy005a.nullMethod();
        } catch ( NullPointerException e ) {
//            suspendpolicy005a.log3("  NullPointerException : " + e);
        }
    }
}
