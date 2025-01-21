/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.Location.lineNumber;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * This class is used as debuggee application for the linenumber001 JDI test.
 */

public class linenumber001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //--------------------------------------------------   log procedures

    static boolean verbMode = false;

    private static void log1(String message) {
        if (verbMode)
            System.err.println("**> debuggee: " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.err.println("!!**> debuggee: " + message);
    }

    //====================================================== test program

    static TestClass obj = new TestClass();

    //----------------------------------------------------   main method

    public static void main (String argv[]) {

        for (int i=0; i<argv.length; i++) {
            if ( argv[i].equals("-vbs") || argv[i].equals("-verbose") ) {
                verbMode = true;
                break;
            }
        }
        log1("debuggee started!");

        // informing a debugger of readyness
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        pipe.println("ready");


        int exitCode = PASSED;
        for (int i = 0; ; i++) {

            String instruction;

            log1("waiting for an instruction from the debugger ...");
            instruction = pipe.readln();
            if (instruction.equals("quit")) {
                log1("'quit' recieved");
                break ;

            } else if (instruction.equals("newcheck")) {
                switch (i) {

    //------------------------------------------------------  section tested

                case 0:
                                pipe.println("checkready");
                                break ;

    //-------------------------------------------------    standard end section

                default:
                                pipe.println("checkend");
                                break ;
                }

            } else {
                logErr("ERRROR: unexpected instruction: " + instruction);
                exitCode = FAILED;
                break ;
            }
        }

        System.exit(exitCode + PASS_BASE);
    }
}

class TestClass {     // minLine

    public  void testmethod (int param) {

        boolean bl1 = false, bl2 = true;
        byte    bt1 = 0,     bt2 = 1;
        char    ch1 = 0,     ch2 = 1;

        return;
    }

    String s0 = Integer.toString(123);
    String s1 = Integer.toString(456);
    String s2 = Integer.toString(789);
    String s3 = Integer.toString(321);
    String s4 = Integer.toString(654);
    String s5 = Integer.toString(987);

    // min and max Lines have to be equal to ones above and below
    // with corresponding comments in the TestClass

    static final int minLine = 118;
    static final int maxLine = 142;

}                   // maxLine
