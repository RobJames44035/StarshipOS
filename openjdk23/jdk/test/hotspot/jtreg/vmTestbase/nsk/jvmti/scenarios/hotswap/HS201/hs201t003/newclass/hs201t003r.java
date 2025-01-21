/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.hotswap.HS201;

import java.io.*;
import java.util.*;
import nsk.share.*;

/**
 * A redefining class.
 */
class hs201t003r {
    // dummy fields used only to verify class file redefinition
    public static int entryMethodFld = 0;
    public static int entryMethod2Fld = 0;

    void entryMethod() {
        entryMethodFld = 1; // modify field from the new method

        entryMethod2();
    }

    void entryMethod2() {
        entryMethod2Fld = 1; // modify field from the new method
    }
}
