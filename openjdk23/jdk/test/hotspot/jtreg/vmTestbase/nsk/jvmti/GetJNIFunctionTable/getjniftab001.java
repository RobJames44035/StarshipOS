/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetJNIFunctionTable;

import java.io.*;
import java.util.*;
import nsk.share.*;

/**
 * The test exercises the JVMTI function GetJNIFunctionTable().
 * It checks the following spec assertion:<br>
 * <code>If SetJNIFunctionTable has been called, the modified
 * (not the original) function table is returned.</code>
 * <p>
 * The assertion is verified twice with modified version and original
 * version of the JNI function GetVersion().<br>
 * Upon the function redirection, the test obtains the function table
 * through the GetJNIFunctionTable() and calls GetVersion() expecting
 * its modified version. Then, upon restoring the original JNI function
 * table, the same check is performed expecting the original version of
 * GetVersion() to be called.
 */
public class getjniftab001 {
    static {
        try {
            System.loadLibrary("getjniftab001");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load \"getjniftab001\" library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native int check();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        return new getjniftab001().runIt(args, out);
    }

    private int runIt(String args[], PrintStream out) {
        return check();
    }
}
