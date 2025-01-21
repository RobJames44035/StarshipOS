/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SetJNIFunctionTable;

import java.io.*;
import java.util.*;
import nsk.share.*;

/**
 * The test exercises the JVMTI function SetJNIFunctionTable().
 * It checks that the function returns the following errors properly:
 * <li> JVMTI_ERROR_NULL_POINTER with NULL parameter
 * <li> JVMTI_ERROR_UNATTACHED_THREAD if current thread is
 * unattached
 * <li> JVMTI_ERROR_INVALID_ENVIRONMENT if the JVMTI environment
 * is disposed
 */
public class setjniftab002 {
    static {
        try {
            System.loadLibrary("setjniftab002");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load \"setjniftab002\" library");
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
        return new setjniftab002().runIt(args, out);
    }

    private int runIt(String args[], PrintStream out) {
        return check();
    }
}
