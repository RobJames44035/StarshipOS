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
public class getvalue005t {
    public static void main(String args[]) {
        System.exit(run(args) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        // force debuggee VM to load dummy class
        getvalue005tDummyCls dummyCls =
            new getvalue005tDummyCls();

        pipe.println(getvalue005.COMMAND_READY);
        String cmd = pipe.readln();
        if (cmd.equals(getvalue005.COMMAND_QUIT)) {
            System.err.println("Debuggee: exiting due to the command: "
                + cmd);
            return Consts.TEST_PASSED;
        }

        int stopMeHere = 0; // getvalue005.DEBUGGEE_STOPATLINE

        cmd = pipe.readln();
        if (!cmd.equals(getvalue005.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            return Consts.TEST_FAILED;
        }
        return Consts.TEST_PASSED;
    }
}

// dummy class used for provoking IllegalArgumentException in debugger
class getvalue005tDummyCls {
    private boolean boolPrFld = true;
    private byte bytePrFld = Byte.MIN_VALUE;
    private char charPrFld = 'z';
    private double doublePrFld = Double.MAX_VALUE;
    private float floatPrFld = Float.MIN_VALUE;
    private int intPrFld = Integer.MIN_VALUE;
    private long longPrFld = Long.MIN_VALUE;
    private short shortPrFld = Short.MAX_VALUE;

    boolean boolFld = true;
    byte byteFld = Byte.MAX_VALUE;
    char charFld = 'w';
    double doubleFld = Double.MIN_VALUE;
    float floatFld = Float.MAX_VALUE;
    int intFld = Integer.MAX_VALUE;
    long longFld = Long.MAX_VALUE;
    short shortFld = Short.MIN_VALUE;

    public boolean boolPubFld = false;
    public byte bytePubFld = 127;
    public char charPubFld = 'a';
    public double doublePubFld = 6.2D;
    public float floatPubFld = 5.1F;
    public int intPubFld = 2147483647;
    public long longPubFld = 9223372036854775807L;
    public short shortPubFld = -32768;

    protected boolean boolProtFld = true;
    protected byte byteProtFld = Byte.MIN_VALUE;
    protected char charProtFld = 'z';
    protected double doubleProtFld = Double.MAX_VALUE;
    protected float floatProtFld = Float.MIN_VALUE;
    protected int intProtFld = Integer.MIN_VALUE;
    protected long longProtFld = Long.MIN_VALUE;
    protected short shortProtFld = Short.MAX_VALUE;
}
