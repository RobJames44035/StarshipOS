/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdb.dump.dump002;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class dump002a {
    static dump002a _dump002a = new dump002a();

    public static void main(String args[]) {
       System.exit(dump002.JCK_STATUS_BASE + _dump002a.runIt(args, System.out));
    }

    static void lastBreak () {}

    public int runIt(String args[], PrintStream out) {
        JdbArgumentHandler argumentHandler = new JdbArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        lastBreak();

        log.display("Debuggee PASSED");
        return dump002.PASSED;
    }

    static     int iStatic    = 0;
    private    int iPrivate   = 1;
    protected  int iProtect   = 2;
    public     int iPublic    = 3;
    final      int iFinal     = 4;
    transient  int iTransient = 5;
    volatile   int iVolatile  = 6;

    static     int [] iArray = { 7 };

    static     String sStatic    = "zero";
    private    String sPrivate   = "one";
    protected  String sProtected = "two";
    public     String sPublic    = "three";
    final      String sFinal     = "four";
    transient  String sTransient = "five";
    volatile   String sVolatile  = "six";

    static     String [] sArray = { "seven" };

    boolean fBoolean = true;
    byte    fByte    = Byte.MAX_VALUE;
    char    fChar    = Character.MAX_VALUE;
    double  fDouble  = Double.MAX_VALUE;
    float   fFloat   = Float.MAX_VALUE;
    int     fInt     = Integer.MAX_VALUE;
    long    fLong    = Long.MAX_VALUE;
    short   fShort   = Short.MAX_VALUE;
}
