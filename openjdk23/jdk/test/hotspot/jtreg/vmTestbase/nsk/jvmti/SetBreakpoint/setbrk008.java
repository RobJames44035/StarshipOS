/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SetBreakpoint;

import java.io.PrintStream;

public class setbrk008 {

    final static int JCK_STATUS_BASE = 95;
    final static int MAX_DEPTH = 100;

    static {
        try {
            System.loadLibrary("setbrk008");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load setbrk008 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void getReady(int n);
    native static int check();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        getReady(MAX_DEPTH);
        checkPoint(1);
        return check();
    }

    public static void checkPoint(int depth) {
        if (depth < MAX_DEPTH) {
            checkPoint(depth + 1);
        }
    }
}
