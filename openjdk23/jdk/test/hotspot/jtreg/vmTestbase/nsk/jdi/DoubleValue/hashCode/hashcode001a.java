/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.DoubleValue.hashCode;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This class is used as debuggee application for the hashcode001a JDI test.
 */

public class hashcode001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //--------------------------------------------------   log procedures

    static boolean verbMode = false;  // debugger may switch to true

    private static void log1(String message) {
        if (verbMode)
            System.err.println("**> hashcode001a: " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.err.println("!!**> hashcode001a: " + message);
    }

    //====================================================== test program
     //................................................... globals for a debugger

//      public static double pos_inf  =  Double.POSITIVE_INFINITY;
//      public static double pos_zero =  0.0d;
//      public static double neg_zero = -0.0d;
//      public static double neg_inf  =  Double.NEGATIVE_INFINITY;

        public static double pos_largest  =  Double.MAX_VALUE;
//      public static double pos_smallest =  Double.MIN_VALUE;
//      public static double neg_largest  = -Double.MAX_VALUE;
//      public static double neg_smallest = -Double.MIN_VALUE;

//      public static double double_nan   =  Double.NaN;

        public static double plus1_1 =  +1.0d;
        public static double plus1_2 =  +1.0d;
//      public static double minus1  =  -1.0d;

//      public static float  float_plus1 = +1.0f;

    //....................................................
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
