/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.unit.events.redefineCFLH;

import java.io.PrintStream;

public class JvmtiTestr {
    public int checkIt(boolean DEBUG_MODE, PrintStream out) {
        if (DEBUG_MODE)
            out.println("NEW JvmtiTestr: inside the checkIt()");
        return 73;
    }
}
