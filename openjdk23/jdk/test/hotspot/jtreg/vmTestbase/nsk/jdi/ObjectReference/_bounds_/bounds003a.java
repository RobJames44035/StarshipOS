/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ObjectReference._bounds_;


import nsk.share.jdi.*;

/**
 *  <code>bounds003a</code> is debuggee's part of the bounds003.
 */
public class bounds003a extends AbstractJDIDebuggee {

    public static String testedFieldName = "fieldObj";

    private byte     byteField;
    private char     charField;
    private double   doubleField;
    private float    floatField;
    private int      intField;
    private long     longField;
    private short    shortField;

    static bounds003a fieldObj = new bounds003a();

    public static void main (String args[]) {
        new bounds003a().doTest(args);
    }
}
