/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ReferenceType.nestedTypes;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the nestedtypes002 JDI test.
 */

public class nestedtypes002a {

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
                                nestedtypes002aTestClass testArray[] = { new nestedtypes002aTestClass() };

                                Class bl = Boolean.TYPE;
                                Class bt = Byte.TYPE;
                                Class ch = Character.TYPE;
                                Class db = Double.TYPE;
                                Class fl = Float.TYPE;
                                Class in = Integer.TYPE;
                                Class ln = Long.TYPE;
                                Class sh = Short.TYPE;

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

class nestedtypes002aTestClass {

    class NestedClass implements NestedIface {
        boolean bnc = true;
    }
    NestedClass  nestedClass        = new NestedClass();
    NestedClass  nestedClassArray[] = { new NestedClass() };

    interface NestedIface {
        boolean bsnf = true;
    }
    NestedIface nestedIface        = new NestedClass();
    NestedIface nestedIfaceArray[] = { new NestedClass() };



    static class StaticNestedClass {
        boolean bsnc = true;
    }
    StaticNestedClass staticNestedClass        = new StaticNestedClass();
    StaticNestedClass staticNestedClassArray[] = { new StaticNestedClass() };

}
