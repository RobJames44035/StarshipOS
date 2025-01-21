/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ObjectReference.setValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This is a debuggee class.
 */
public class setvalue003t {
    // tested static fields
    static byte    sByteFld = Byte.MAX_VALUE;
    static short   sShortFld = Short.MAX_VALUE;
    static int     sIntFld = Integer.MAX_VALUE;
    static long    sLongFld = Long.MAX_VALUE;
    static float   sFloatFld = Float.MAX_VALUE;
    static double  sDoubleFld = Double.MAX_VALUE;
    static char    sCharFld = Character.MAX_VALUE;
    static boolean sBooleanFld = true;
    static String  sStrFld = "static field";

    // tested instance fields
    byte    byteFld = sByteFld;
    short   shortFld = sShortFld;
    int     intFld = sIntFld;
    long    longFld = sLongFld;
    float   floatFld = sFloatFld;
    double  doubleFld = sDoubleFld;
    char    charFld = sCharFld;
    boolean booleanFld = false;
    String  strFld = "instance field";


    public static void main(String args[]) {
        System.exit(run(args) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[]) {
        return new setvalue003t().runIt(args);
    }

    private int runIt(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe setvalue003tPipe = argHandler.createDebugeeIOPipe();

        Thread.currentThread().setName(setvalue003.DEBUGGEE_THRNAME);

        setvalue003tPipe.println(setvalue003.COMMAND_READY);
        String cmd = setvalue003tPipe.readln();
        if (!cmd.equals(setvalue003.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            System.exit(Consts.JCK_STATUS_BASE +
                Consts.TEST_FAILED);
        }
        return Consts.TEST_PASSED;
    }
}
