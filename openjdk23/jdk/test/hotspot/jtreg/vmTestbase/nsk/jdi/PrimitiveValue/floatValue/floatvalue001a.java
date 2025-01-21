/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.PrimitiveValue.floatValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This class is used as debuggee application for the floatvalue001 JDI test.
 */

public class floatvalue001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //--------------------------------------------------   log procedures

    static boolean verbMode = false;  // debugger may switch to true

    private static void log1(String message) {
        if (verbMode)
            System.err.println("**> floatvalue001a: " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.err.println("!!**> floatvalue001a: " + message);
    }

    //====================================================== test program

    static boolean bl1 = true;
    static boolean bl0 = false;
    static byte    bt1 = Byte.MAX_VALUE;
    static byte    bt0 = 0;
    static char    ch1 = Character.MAX_VALUE;
    static char    ch0 = 0;
    static double  db1 = Double.MAX_VALUE;
    static double  db0 = 0.0d;
    static float   fl1 = Float.MAX_VALUE;
    static float   fl0 = 0.0f;
    static int     in1 = Integer.MAX_VALUE;
    static int     in0 = 0;
    static long    ln1 = Long.MAX_VALUE;
    static long    ln0 = 0;
    static short   sh1 = Short.MAX_VALUE;
    static short   sh0 = 0;

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
