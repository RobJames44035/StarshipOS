/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.BooleanValue.equals;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>equals002a</code> is deugee's part of the test.<br>
 *  It contains the static fields with boundary values for each primitive type.<br>
 *  <code>ClassToCheck</code> delivers values for first parameter of <code>equals</code>.<br>
 */
public class equals002a {

    static ClassToCheck testedObj = new ClassToCheck();
    static String[] testedFields = {
                                    "cmpObjNULL",
                                    "cmpObject",
                                    "cmpBoolMAX",
                                    "cmpBoolMIN",
                                    "cmpByteMAX",
                                    "cmpByteMIN",
                                    "cmpCharMAX",
                                    "cmpCharMIN",
                                    "cmpDoubleMAX",
                                    "cmpDoubleMIN",
                                    "cmpFloatMAX",
                                    "cmpFloatMIN",
                                    "cmpIntMAX",
                                    "cmpIntMIN",
                                    "cmpLongMAX",
                                    "cmpLongMIN",
                                    "cmpShortMAX",
                                    "cmpShortMIN"
    };

    static Object   cmpObjNULL  = null;
    static Object   cmpObject   = new Object();
    static boolean  cmpBoolMAX  = true;
    static boolean  cmpBoolMIN  = false;
    static byte     cmpByteMAX  = Byte.MAX_VALUE;
    static byte     cmpByteMIN  = Byte.MIN_VALUE;
    static char     cmpCharMAX  = Character.MAX_VALUE;
    static char     cmpCharMIN  = Character.MIN_VALUE;
    static double   cmpDoubleMAX= Double.MAX_VALUE;
    static double   cmpDoubleMIN= Double.MIN_VALUE;
    static float    cmpFloatMAX = Float.MAX_VALUE;
    static float    cmpFloatMIN = Float.MIN_VALUE;
    static int      cmpIntMAX   = Integer.MAX_VALUE;
    static int      cmpIntMIN   = Integer.MIN_VALUE;
    static long     cmpLongMAX  = Long.MAX_VALUE;
    static long     cmpLongMIN  = Long.MIN_VALUE;
    static short    cmpShortMAX = Short.MAX_VALUE;
    static short    cmpShortMIN = Short.MIN_VALUE;

    public static void main (String argv[]) {

        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println("ready");

        String instruction = pipe.readln();

        if ( instruction.equals("quit") ) {
            log.display("DEBUGEE> \"quit\" signal received.");
            log.display("DEBUGEE> completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }
        log.complain("DEBUGEE FAILURE> unexpected signal "
                         + "(no \"quit\") - " + instruction);
        log.complain("DEBUGEE FAILURE> TEST FAILED");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }
}

class ClassToCheck {
    public      boolean boolTRUE  = true;
    public      boolean boolFALSE = false;
}
