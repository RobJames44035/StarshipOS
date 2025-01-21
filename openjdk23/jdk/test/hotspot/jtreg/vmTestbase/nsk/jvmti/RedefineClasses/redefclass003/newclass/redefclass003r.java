/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.RedefineClasses;

import java.io.PrintStream;

public class redefclass003r {
    static public byte byteFld = 10;
    static public short shortFld = 20;
    static public int intFld = 30;
    static public long longFld = 40L;
    static public float floatFld = 50.2F;
    static public double doubleFld = 60.3D;
    static public char charFld = 'b';
    static public boolean booleanFld = true;
    static public String stringFld = "NEW redefclass003r";
// completely new static variables are below
    static public byte byteComplNewFld = 11;
    static public short shortComplNewFld = 22;
    static public int intComplNewFld = 33;
    static public long longComplNewFld = 44L;
    static public float floatComplNewFld = 55.5F;
    static public double doubleComplNewFld = 66.6D;
    static public char charComplNewFld = 'c';
    static public boolean booleanComplNewFld = false;
    static public String stringComplNewFld = "completely new field";

    public int checkIt(PrintStream out, boolean DEBUG_MODE) {
        if (DEBUG_MODE)
            out.println("NEW redefclass003r: inside the checkIt()");
        return 73;
    }
}
