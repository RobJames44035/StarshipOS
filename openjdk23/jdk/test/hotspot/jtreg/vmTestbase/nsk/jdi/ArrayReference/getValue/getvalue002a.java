/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */


package nsk.jdi.ArrayReference.getValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


public class getvalue002a {
    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        getvalue002aClassToCheck classToCheck = new getvalue002aClassToCheck();

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

class getvalue002aClassToCheck {
    static boolean z1[] = {true, false, false, true, true};
    static byte    b1[] = {Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE};
    static char    c1[] = {Character.MIN_VALUE, '\u00ff', '\uff00',
                           '\u1234', '\u4321', Character.MAX_VALUE};
    static double  d1[] = {Double.NEGATIVE_INFINITY};
    static float   f1[] = {Float.POSITIVE_INFINITY};
    static int     i1[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    static long    l1[] = {Long.MIN_VALUE, Long.MAX_VALUE};
    static short   r1[] = {-2, -1, 0, 1, 2};

    static final     long lF1[] = {};
    static private   long lP1[] = {-1, 0};
    static public    long lU1[] = {0, 1, 2};
    static protected long lR1[] = {0, 1, 2, 3};
    static transient long lT1[] = {1, 2, 3, 4, 5};
    static volatile  long lV1[] = {1, 2, 3, 4, 5, 6};
}
