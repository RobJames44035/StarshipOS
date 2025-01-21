/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package sun.jvm.hotspot.code;

import java.io.PrintStream;

/** A placeholder value that has no concrete meaning other than helping constructing
 * other values.
 */
public class MarkerValue extends ScopeValue {
    public boolean isMarker() { return true; }

    public void printOn(PrintStream tty) {
        tty.print("marker");
    }
}
