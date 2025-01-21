/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.AddToBootstrapClassLoaderSearch;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

/** Negative superclass for debuggee class. */
public class bootclssearch002p {
    // scaffold objects
    int status = Consts.TEST_FAILED;

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        out.println("ERROR: Debuggee class was loaded from segment added with AddToBootstrapClassSearchPath(),");
        out.println("ERROR: but its superclass was loaded from usual classpath, not from bootstrap classpath,");
        out.println("ERROR: added with option +Xbootclasspath/a");
        return status;
    }
}
