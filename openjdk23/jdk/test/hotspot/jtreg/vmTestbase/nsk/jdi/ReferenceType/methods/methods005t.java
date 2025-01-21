/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ReferenceType.methods;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * This is debuggee class.
 */
public class methods005t {
    public static void main(String args[]) {
        System.exit(run(args) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(methods005.COMMAND_READY);
        String cmd = pipe.readln();
        if (cmd.equals(methods005.COMMAND_QUIT)) {
            System.err.println("Debuggee: exiting due to the command: "
                + cmd);
            return Consts.TEST_PASSED;
        }

        int stopMeHere = 0; // methods005.DEBUGGEE_STOPATLINE

        cmd = pipe.readln();
        if (!cmd.equals(methods005.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            return Consts.TEST_FAILED;
        }
        return Consts.TEST_PASSED;
    }

    // classes representing primitive types & arrays used to check
    // an assertion in the debugger
    static Class boolCls = Boolean.TYPE;
    static Class byteCls = Byte.TYPE;
    static Class charCls = Character.TYPE;
    static Class doubleCls = Double.TYPE;
    static Class floatCls = Float.TYPE;
    static Class intCls = Integer.TYPE;
    static Class longCls = Long.TYPE;
    static Class shortCls = Short.TYPE;

    static Boolean boolClsArr[] = {Boolean.valueOf(false)};
    static Byte byteClsArr[] = {Byte.valueOf((byte) 127)};
    static Character charClsArr[] = {Character.valueOf('a')};
    static Double doubleClsArr[] = {Double.valueOf(6.2D)};
    static Float floatClsArr[] = {Float.valueOf(5.1F)};
    static Integer intClsArr[] = {Integer.valueOf(2147483647)};
    static Long longClsArr[] = {Long.valueOf(9223372036854775807L)};
    static Short shortClsArr[] = {Short.valueOf((short) -32768)};

    static boolean boolArr[] = {true};
    static byte byteArr[] = {Byte.MAX_VALUE};
    static char charArr[] = {'z'};
    static double doubleArr[] = {Double.MAX_VALUE};
    static float floatArr[] = {Float.MAX_VALUE};
    static int intArr[] = {Integer.MAX_VALUE};
    static long longArr[] = {Long.MAX_VALUE};
    static short shortArr[] = {Short.MAX_VALUE};
}
