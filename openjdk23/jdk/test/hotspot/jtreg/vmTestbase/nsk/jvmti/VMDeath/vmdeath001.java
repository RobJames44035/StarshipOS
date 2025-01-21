/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.VMDeath;

import java.io.*;
import java.util.*;
import nsk.share.*;

/**
 * The test exercises the JVMTI event VM Death.<br>
 * It verifies that the event will be sent and if so,
 * it happens during the live phase of VM execution.
 */
public class vmdeath001 {
    static {
        try {
            System.loadLibrary("vmdeath001");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load \"vmdeath001\" library");
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
        return new vmdeath001().runIt(args, out);
    }

    private int runIt(String args[], PrintStream out) {
        // Note: FAILED status will be returned only
        // if there is no VMDeath event
        return Consts.TEST_FAILED;
    }
}
