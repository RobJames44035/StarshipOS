/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.BooleanValue.value;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This class is used as debugee application for the value001a JDI test.
 */

public class value001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;


     //--------------------------------------------------   log procedures

    private static boolean verbMode = false;

    private static void log1(String message) {
        if (verbMode)
            System.err.println("**> value001a: " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.err.println("!!**> value001a: " + message);
    }

    //====================================================== test program

    //................................................... globals for a debugger

        public static boolean bTrue1  = true;
        public static boolean bTrue2  = true;
        public static boolean bFalse1 = false;
        public static boolean bFalse2 = false;

    //....................................................

    //----------------------------------------------------   main method

    public static void main (String argv[]) {

        for (int i=0; i<argv.length; i++) {
            if ( argv[i].equals("-vbs") || argv[i].equals("-verbose") ) {
                verbMode = true;
                break;
            }
        }
        log1("debugee started!");

        // informing debuger of readyness
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        pipe.println("ready");


        int exitCode = PASSED;
        for (int i = 0; ; i++) {

            String instruction;

            log1("waiting for an instruction from the debuger ...");
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
                exitCode = 2;
                break ;
            }
        }

        System.exit(exitCode + PASS_BASE);
    }
}
