/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SetLocalVariable;

import java.io.PrintStream;

public class setlocal003 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("setlocal003");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load setlocal003 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void getReady();
    native static int getRes();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        getReady();
        Object o = new Object();
        int i = 1;
        long l = 2;
        float f = 3.0F;
        double d = 4.0;
        checkPoint();
        return getRes();
    }

    // dummy method to be breakpointed in agent
    public static void checkPoint() {
    }
}
