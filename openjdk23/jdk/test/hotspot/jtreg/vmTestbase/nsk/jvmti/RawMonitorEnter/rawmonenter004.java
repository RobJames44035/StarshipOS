/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.RawMonitorEnter;

import java.io.PrintStream;

public class rawmonenter004 {

    final static int JCK_STATUS_BASE = 95;
    final static int THREADS_LIMIT = 100;

    static {
        try {
            System.loadLibrary("rawmonenter004");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load rawmonenter004 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int check(Thread threads[], int waitTime);

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }


    public static int run(String args[], PrintStream out) {
        int waitTime = 2;
        if (args.length > 0) {
            try {
                int i  = Integer.parseInt(args[0]);
                waitTime = i;
            } catch (NumberFormatException ex) {
                out.println("# Wrong argument \"" + args[0]
                    + "\", the default value is used");
            }
        }
        out.println("# Waiting time = " + waitTime + " mins");

        Thread threads[] = new Thread[THREADS_LIMIT];
        for (int i = 0; i < THREADS_LIMIT; i++) {
            threads[i] = new Thread();
        }
        return check(threads, waitTime);
    }
}
