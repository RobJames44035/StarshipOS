/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jdwp.Method.IsObsolete;

import nsk.share.*;

/**
 * This class is to be redefined.
 */
public class isobsolete002b {

    // static field
    public static int staticField = 0;
    // object field
    public int objectField = 0;

    public static Log log;

    // tested method to be redefined
    public void testedMethod(int arg) {
        int value = arg; // isobsolete002a.BREAKPOINT_LINE
        log.display("Object method invoked: NOT_REDEFINED");
    }
}
