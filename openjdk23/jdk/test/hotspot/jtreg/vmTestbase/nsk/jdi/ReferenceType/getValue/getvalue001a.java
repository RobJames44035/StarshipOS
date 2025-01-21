/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ReferenceType.getValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the getvalue001 JDI test.
 */

public class getvalue001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //--------------------------------------------------   log procedures

    static boolean verbMode = false;

    private static void log1(String message) {
        if (verbMode)
            System.out.println("**>  debuggee: " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.out.println("!!**>  debuggee: " + message);
    }

    //====================================================== test program
    //------------------------------------------------------ common section
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

        for (int i=0; i<argv.length; i++) {
            if ( argv[i].equals("-vbs") || argv[i].equals("-verbose") ) {
                verbMode = true;
                break;
            }
        }
        log1("debuggee started!");

        int exitCode = PASSED;


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
                                getvalue001aTestClass testObj = new getvalue001aTestClass();
                                methodForCommunication();
                                break ;

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

interface getvalue001aInterface1 {
    char    ch0 = 0;
    double  db0 = 0.0d;
}
interface getvalue001aInterface0 extends getvalue001aInterface1 {
    boolean bl0 = false;
    byte    bt0 = 0;
}
interface getvalue001aInterface2 {
    long    ln0 = 0;
}
class getvalue001aClass1 implements getvalue001aInterface2 {
    static short   sh0 = 0;
}
class getvalue001aTestClass extends getvalue001aClass1 implements getvalue001aInterface0 {
    static float   fl0 = 0.0f;
    static int     in0 = 0;
}
