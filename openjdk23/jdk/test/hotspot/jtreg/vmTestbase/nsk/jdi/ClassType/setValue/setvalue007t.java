/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ClassType.setValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This is debuggee class.
 */
public class setvalue007t {
    // dummy static fields used by debugger for testing
    static byte    byteSFld = Byte.MAX_VALUE;
    static short   shortSFld = Short.MAX_VALUE;
    static int     intSFld = Integer.MAX_VALUE;
    static long    longSFld = Long.MAX_VALUE;
    static float   floatSFld = Float.MAX_VALUE;
    static double  doubleSFld = Double.MAX_VALUE;
    static char    charSFld = 'a';
    static boolean booleanSFld = false;
    static String  strSFld = "static field";
    static setvalue007tDummyType dummySFld = new setvalue007tDummyType();

    public static void main(String args[]) {
        System.exit(run(args) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[]) {
        return new setvalue007t().runIt(args);
    }

    private int runIt(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        Log log = argHandler.createDebugeeLog();
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(setvalue007.COMMAND_READY);
        String cmd = pipe.readln();
        if (!cmd.equals(setvalue007.COMMAND_QUIT)) {
            log.complain("TEST BUG: unknown debugger command: "
                + cmd);
            return Consts.TEST_FAILED;
        }
        return Consts.TEST_PASSED;
    }
}

// Dummy reference type used by debugger for testing
class setvalue007tDummyType {}
