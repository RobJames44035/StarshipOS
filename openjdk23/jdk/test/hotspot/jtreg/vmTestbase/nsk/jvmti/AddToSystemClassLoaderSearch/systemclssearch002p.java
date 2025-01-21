/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.jvmti.AddToSystemClassLoaderSearch;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

/** Positive superclass for debuggee class. */
public class systemclssearch002p {
    // scaffold objects
    int status = Consts.TEST_PASSED;

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        out.println("Positive implementation of superclass was found and loaded");
        return status;
    }
}
