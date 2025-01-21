/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.RunAgentThread;

import java.io.PrintStream;

public class agentthr002 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("agentthr002");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load agentthr002 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int check(Thread thr);

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        return check(new Thread());
    }
}
