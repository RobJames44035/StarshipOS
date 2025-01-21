/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.InterruptThread;

import java.io.PrintStream;

public class intrpthrd003 {
    private final static String AGENT_LIB = "intrpthrd003";
    private final static int DEF_TIME_MAX = 30;  // default max # secs to test

    final static int THREADS_NUMBER = 32;
    final static int N_LATE_CALLS = 1000;

    static {
        try {
            System.loadLibrary(AGENT_LIB);
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load " + AGENT_LIB + " library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int check(long ind, Thread thr);
    native static int getResult();
    native static boolean isThreadNotAliveError();

    public static void main(String[] args) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        System.exit(run(args, System.out) + 95/*STATUS_TEMP*/);
    }

    public static int run(String args[], PrintStream ref) {
        int timeMax = 0;
        if (args.length == 0) {
            timeMax = DEF_TIME_MAX;
        } else {
            try {
                timeMax = Integer.parseUnsignedInt(args[0]);
            } catch (NumberFormatException nfe) {
                System.err.println("'" + args[0] + "': invalid timeMax value.");
                usage();
            }
        }

        System.out.println("About to execute for " + timeMax + " seconds.");

        long count = 0;
        long start_time = System.currentTimeMillis();
        while (System.currentTimeMillis() < start_time + (timeMax * 1000)) {
            count++;

            intrpthrd003a thr = new intrpthrd003a();
            synchronized (thr.syncObject) {
                thr.start();
                try {
                    thr.syncObject.wait();

                    while (true) {
                        if (check(count, thr) == 2) break;

                        if (isThreadNotAliveError()) {
                            // Done with InterruptThread() calls since
                            // thread is not alive.
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    throw new Error("Unexpected: " + e);
                }
            }

            try {
                thr.join();
            } catch (InterruptedException e) {
                throw new Error("Unexpected: " + e);
            }
            if (check(count, thr) == 2) break;
        }

        System.out.println("Executed " + count + " loops in " + timeMax +
                           " seconds.");

        return getResult();
    }

    public static void usage() {
        System.err.println("Usage: " + AGENT_LIB + " [time_max]");
        System.err.println("where:");
        System.err.println("    time_max  max looping time in seconds");
        System.err.println("              (default is " + DEF_TIME_MAX +
                           " seconds)");
        System.exit(1);
    }
}

class intrpthrd003a extends Thread {
    public Object syncObject = new Object();

    public void run() {
        synchronized (syncObject) {
            syncObject.notify();
        }
    }
}
