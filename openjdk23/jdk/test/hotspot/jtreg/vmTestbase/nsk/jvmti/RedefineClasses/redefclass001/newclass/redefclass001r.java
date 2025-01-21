/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.RedefineClasses;

import java.io.PrintStream;

public class redefclass001r {
    public int checkIt(boolean DEBUG_MODE, PrintStream out) {
        if (DEBUG_MODE)
            out.println("NEW redefclass001r: inside the checkIt()");
        return 73;
    }
}
