/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ReferenceType.isFinal;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the isfinal001 JDI test.
 */

public class isfinal001a {

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
                                TestClass check = new TestClass();
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

class TestClass {

    class InnerClass implements NestedIface {
        boolean bic = true;
    }
    InnerClass  innerClass        = new InnerClass();
    InnerClass  innerClassArray[] = { new InnerClass() };

    final class FinalInnerClass {
        boolean bfic = true;
    }
    FinalInnerClass finalInnerClass        = new FinalInnerClass();
    FinalInnerClass finalInnerClassArray[] = { new FinalInnerClass() };

    interface NestedIface {
        boolean bif = true;
    }
    NestedIface nestedIface        = new InnerClass();
    NestedIface nestedIfaceArray[] = { new InnerClass() };

    static OuterClass outerClass        = new OuterClass();
    static OuterClass outerClassArray[] = { new OuterClass() };
    static OuterIface outerIface        = new OuterClass();
    static OuterIface outerIfaceArray[] = { new OuterClass() };

    static FinalOuterClass finalOuterClass        = new FinalOuterClass();
    static FinalOuterClass finalOuterClassArray[] = { new FinalOuterClass() };

    boolean bl[][][][] = {{{{true}}}};
    byte    bt[][][][] = {{{{0}}}};
    char    ch[][][][] = {{{{0}}}};
    double  db[][][][] = {{{{0.0}}}};
    float   fl[][][][] = {{{{0.0f}}}};
    int     in[][][][] = {{{{0}}}};
    long    ln[][][][] = {{{{0}}}};
    short   sh[][][][] = {{{{0}}}};

    Boolean   blBl = Boolean.valueOf(true);
    Byte      btBt = Byte.valueOf((byte)1);
    Character chCh = Character.valueOf('c');
    Double    dbDb = Double.valueOf(0);
    Float     flFl = Float.valueOf(0.0f);
    Integer   inIn = Integer.valueOf(0);
    Long      lnLn = Long.valueOf(0);
    Short     shSh = Short.valueOf((short)1);
}

interface OuterIface {
    static boolean b1 = false;
}

class OuterClass implements OuterIface {
    static final boolean b2 = true;
}

final class FinalOuterClass implements OuterIface {
    static final boolean b3 = true;
}
