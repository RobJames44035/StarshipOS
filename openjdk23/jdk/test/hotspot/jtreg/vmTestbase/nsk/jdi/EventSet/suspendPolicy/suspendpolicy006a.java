/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.EventSet.suspendPolicy;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the suspendpolicy006 JDI test.
 */

public class suspendpolicy006a {

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

    static suspendpolicy006aTestClass tcObject = new suspendpolicy006aTestClass();

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

                    case 0: suspendpolicy006aTestClass.method();
                            break;

                    case 1: suspendpolicy006aTestClass.method();
                            break;

                    case 2: suspendpolicy006aTestClass.method();
                            break;

                    case 3: suspendpolicy006aTestClass.method();
                            break;

                    case 4: suspendpolicy006aTestClass.method();
                            break;

                    case 5: suspendpolicy006aTestClass.method();
                            break;

                    case 6: suspendpolicy006aTestClass.method();

    //-------------------------------------------------    standard end section

                    default:
                                instruction = end;
                                methodForCommunication();
                                break label0;
                }
            }

        System.exit(exitCode + PASS_BASE);
    }

}
class suspendpolicy006aTestClass {

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
