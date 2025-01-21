/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ReferenceType.getValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the getvalue003 JDI test.
 */

public class getvalue003a {

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
                                getvalue003aTestClass testObj = new getvalue003aTestClass();
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

interface getvalue003aInterface1 {
    boolean[][][][] bl1 = { {{{true, false}, {true, false}}, {{true, false}, {true, false}} },
                                     {{{true, false}, {true, false}}, {{true, false}, {true, false}} }  };
    byte   [][][][] bt1 = { {{{0, 1},{0, 1}}, {{0, 1},{0, 1}}},
                                     {{{0, 1},{0, 1}}, {{0, 1},{0, 1}}}  };
}

interface getvalue003aInterface0 extends getvalue003aInterface1 {
    char   [][][][] ch1 = { {{{0, 1},{0, 1}}, {{0, 1},{0, 1}}},
                                     {{{0, 1},{0, 1}}, {{0, 1},{0, 1}}}  };
    double [][][][] db1 = { {{{0.0d, 1.0d},{0.0d, 1.0d}}, {{0.0d, 1.0d},{0.0d, 1.0d}}},
                                     {{{0.0d, 1.0d},{0.0d, 1.0d}}, {{0.0d, 1.0d},{0.0d, 1.0d}}}  };
}

interface getvalue003aInterface2 {
    float  [][][][] fl1 = { {{{0.0f, 1.0f},{0.0f, 1.0f}}, {{0.0f, 1.0f},{0.0f, 1.0f}}},
                                     {{{0.0f, 1.0f},{0.0f, 1.0f}}, {{0.0f, 1.0f},{0.0f, 1.0f}}}  };
}

class getvalue003aClass1 implements getvalue003aInterface2 {
    static int    [][][][] in1 = { {{{0, 1},{0, 1}}, {{0, 1},{0, 1}}},
                                     {{{0, 1},{0, 1}}, {{0, 1},{0, 1}}}  };
}

class getvalue003aTestClass extends getvalue003aClass1 implements getvalue003aInterface0 {
    static final long   [][][][] ln1 = { {{{0, 1},{0, 1}}, {{0, 1},{0, 1}}},
                                     {{{0, 1},{0, 1}}, {{0, 1},{0, 1}}}  };
    static final short  [][][][] sh1 = { {{{0, 1},{0, 1}}, {{0, 1},{0, 1}}},
                                     {{{0, 1},{0, 1}}, {{0, 1},{0, 1}}}  };

    boolean[][][][] bl0;
    byte   [][][][] bt0;
    char   [][][][] ch0;
    double [][][][] db0;
    float  [][][][] fl0;

    public getvalue003aTestClass () {
        bl0 = bl1;
        bt0 = bt1;
        ch0 = ch1;
        db0 = db1;
        fl0 = fl1;
    }
}
