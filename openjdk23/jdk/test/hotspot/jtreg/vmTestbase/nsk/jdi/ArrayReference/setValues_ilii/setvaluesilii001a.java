/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */


package nsk.jdi.ArrayReference.setValues_ilii;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


public class setvaluesilii001a {
    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        setvaluesilii001aClassToCheck classToCheck = new setvaluesilii001aClassToCheck();

        log.display("DEBUGEE> debugee started.");
        pipe.println("ready");
        String instruction = pipe.readln();
        if (instruction.equals("quit")) {
            log.display("DEBUGEE> \"quit\" signal recieved.");
            log.display("DEBUGEE> completed succesfully.");
            System.exit(95);
        }
        log.complain("DEBUGEE FAILURE> unexpected signal "
                         + "(no \"quit\") - " + instruction);
        log.complain("DEBUGEE FAILURE> TEST FAILED");
        System.exit(97);
    }
}

class setvaluesilii001aClassToCheck {
    static double  d1S[] = {Double.NEGATIVE_INFINITY, Double.MIN_VALUE, -1, -0,
                            0, 1,  Double.MAX_VALUE, Double.POSITIVE_INFINITY,
                            Double.NaN, Double.NaN, -0, 0, 1, -1};
    static float   f1S[] = {Float.NEGATIVE_INFINITY, Float.MIN_VALUE, -1, -0, 0,
                            1,  Float.MAX_VALUE, Float.POSITIVE_INFINITY,
                            Float.NaN, Float.NaN, -0, 1, -1,
                            14};
    static byte    b1S[] = {Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE,
                            Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE,
                            Byte.MIN_VALUE, -1,
                            13, 14};
    static int     i1S[] = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE,
                            Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE, -1,
                            12, 13, 14};
    static long    l1S[] = {Long.MIN_VALUE, -1, 0, 1, Long.MAX_VALUE,
                            Long.MIN_VALUE, -1, 0, 1, Long.MAX_VALUE,
                            11, 12, 13, 14};
    static short   r1S[] = {Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE,
                            Short.MIN_VALUE, -1, 0, 1,
                            10, 11, 12, 13, 14};
    static char    c1S[] = {Character.MIN_VALUE, '\u00ff', '\uff00', '\u1234',
                            '\u4321', '\ufedc', '\ucdef', Character.MAX_VALUE,
                            9, 10, 11, 12, 13, 14};
    static boolean z1S[] = {true,  false, false, true,  true,   true, true,
                            false, true,
                            false, false, false, false, false, false, false};

    static boolean z1[]  = {false, false, false, false, false, false, false,
                            false, false, false, false, false, false, false};
    static byte    b1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    static char    c1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    static double  d1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    static float   f1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    static int     i1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    static long    l1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    static short   r1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    static final     long lF1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                                    13, 14};
    static private   long lP1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                                    13, 14};
    static public    long lU1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                                    13, 14};
    static protected long lR1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                                    13, 14};
    static transient long lT1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                                    13, 14};
    static volatile  long lV1[]  = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                                    13, 14};
}
