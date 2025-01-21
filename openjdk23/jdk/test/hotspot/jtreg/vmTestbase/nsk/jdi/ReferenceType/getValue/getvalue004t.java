/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ReferenceType.getValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * This is debuggee class.
 */
public class getvalue004t {
    public static void main(String args[]) {
        System.exit(run(args) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        // force debuggee VM to load dummy classes
        getvalue004tDummySuperCls dummySuperCls =
            new getvalue004tDummySuperCls();
        getvalue004tDummyCls dummyCls =
            new getvalue004tDummyCls();

        pipe.println(getvalue004.COMMAND_READY);
        String cmd = pipe.readln();
        if (cmd.equals(getvalue004.COMMAND_QUIT)) {
            System.err.println("Debuggee: exiting due to the command: "
                + cmd);
            return Consts.TEST_PASSED;
        }

        int stopMeHere = 0; // getvalue004.DEBUGGEE_STOPATLINE

        cmd = pipe.readln();
        if (!cmd.equals(getvalue004.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            return Consts.TEST_FAILED;
        }
        return Consts.TEST_PASSED;
    }
}

// dummy super class used for provoking IllegalArgumentException in debugger
class getvalue004tDummySuperCls {
    private static boolean boolPrFld = true;
    private static byte bytePrFld = Byte.MIN_VALUE;
    private static char charPrFld = 'z';
    private static double doublePrFld = Double.MAX_VALUE;
    private static float floatPrFld = Float.MIN_VALUE;
    private static int intPrFld = Integer.MIN_VALUE;
    private static long longPrFld = Long.MIN_VALUE;
    private static short shortPrFld = Short.MAX_VALUE;
}

// dummy class used for provoking IllegalArgumentException in debugger
class getvalue004tDummyCls extends getvalue004tDummySuperCls {
    static boolean boolFld = false;
    static byte byteFld = 127;
    static char charFld = 'a';
    static double doubleFld = 6.2D;
    static float floatFld = 5.1F;
    static int intFld = 2147483647;
    static long longFld = 9223372036854775807L;
    static short shortFld = -32768;
}
