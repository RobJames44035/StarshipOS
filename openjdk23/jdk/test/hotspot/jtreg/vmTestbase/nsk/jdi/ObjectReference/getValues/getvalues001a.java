/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ObjectReference.getValues;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the getvalues001 JDI test.
 */

public class getvalues001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //--------------------------------------------------   log procedures

    static boolean verbMode = false;

    private static void log1(String message) {
        if (verbMode)
            System.err.println("**>  debuggee: " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.err.println("!!**>  debuggee: " + message);
    }

    //====================================================== test program

    static getvalues001aTestClass testObj = new getvalues001aTestClass();

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

class getvalues001aTestClass1 {

    static float   fl1 = 0.0f;
    static int     in1 = 0;
    static long    ln1 = 0;
    static short   sh1 = 0;

           float   fl2 = 1111111111.0f;
           int     in2 = 1;
           long    ln2 = 0x1234567890abcdefL;
           short   sh2 = 1;

}

class getvalues001aTestClass extends getvalues001aTestClass1 {

    static boolean bl1 = true;
    static byte    bt1 = 0;
    static char    ch1 = 0;
    static double  db1 = 0.0d;

           boolean bl2 = false;
           byte    bt2 = 1;
           char    ch2 = 1;
           double  db2 = 1111111111.0d;
}
