/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.IntegerValue.equals;

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
                                    "cmpByte1",
                                    "cmpByte0",
                                    "cmpByte_1",
                                    "cmpByteMIN",
                                    "cmpCharMAX",
                                    "cmpCharMIN",
                                    "cmpDoubleMAX",
                                    "cmpDouble1",
                                    "cmpDouble0",
                                    "cmpDouble_1",
                                    "cmpDoubleMIN",
                                    "cmpFloatMAX",
                                    "cmpFloat1",
                                    "cmpFloat0",
                                    "cmpFloat_1",
                                    "cmpFloatMIN",
                                    "cmpIntMAX",
                                    "cmpInt1",
                                    "cmpInt0",
                                    "cmpInt_1",
                                    "cmpIntMIN",
                                    "cmpLongMAX",
                                    "cmpLong1",
                                    "cmpLong0",
                                    "cmpLong_1",
                                    "cmpLongMIN",
                                    "cmpShortMAX",
                                    "cmpShort1",
                                    "cmpShort0",
                                    "cmpShort_1",
                                    "cmpShortMIN"
    };

    static Object   cmpObjNULL  = null;
    static Object   cmpObject   = new Object();
    static boolean  cmpBoolMAX  = true;
    static boolean  cmpBoolMIN  = false;
    static byte     cmpByteMAX  = Byte.MAX_VALUE;
    static byte     cmpByte1    = 1;
    static byte     cmpByte0    = 0;
    static byte     cmpByte_1   = -1;
    static byte     cmpByteMIN  = Byte.MIN_VALUE;
    static char     cmpCharMAX  = Character.MAX_VALUE;
    static char     cmpCharMIN  = Character.MIN_VALUE;
    static double   cmpDoubleMAX= Double.MAX_VALUE;
    static double   cmpDouble1  = 1;
    static double   cmpDouble0  = 0;
    static double   cmpDouble_1 = -1;
    static double   cmpDoubleMIN= Double.MIN_VALUE;
    static float    cmpFloatMAX = Float.MAX_VALUE;
    static float    cmpFloat1   = 1;
    static float    cmpFloat0   = 0;
    static float    cmpFloat_1  = -1;
    static float    cmpFloatMIN = Float.MIN_VALUE;
    static int      cmpIntMAX   = Integer.MAX_VALUE;
    static int      cmpInt1     = 1;
    static int      cmpInt0     = 0;
    static int      cmpInt_1    = -1;
    static int      cmpIntMIN   = Integer.MIN_VALUE;
    static long     cmpLongMAX  = Long.MAX_VALUE;
    static long     cmpLong1    = 1;
    static long     cmpLong0    = 0;
    static long     cmpLong_1   = -1;
    static long     cmpLongMIN  = Long.MIN_VALUE;
    static short    cmpShortMAX = Short.MAX_VALUE;
    static short    cmpShort1   = 1;
    static short    cmpShort0   = 0;
    static short    cmpShort_1  = -1;
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
    public      int integerMAX = Integer.MAX_VALUE;
    public      int int1   = 1;
    public      int int0   = 0;
    public      int int_1  = -1;
    public      int integerMIN = Integer.MIN_VALUE;
}
