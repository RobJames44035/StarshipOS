/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.RunAgentThread;

import java.io.PrintStream;

public class agentthr001 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("agentthr001");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load agentthr001 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void startSysThr();
    native static boolean isOver();
    native static int getRes();

    public static int waitTime = 2;

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
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

        Thread thr = new Thread(new agentthr001a(), "thr1");
        thr.start();
        try {
            thr.join(waitTime * 60000);
        } catch (InterruptedException e) {
            throw new Error("Unexpected: " + e);
        }
        return getRes();
    }
}

class agentthr001a implements Runnable {

    final static int WAITING_TIME = 100;

    public synchronized void run() {
        agentthr001.startSysThr();
        while (!agentthr001.isOver()) {
            try {
                this.wait(WAITING_TIME);
            } catch (InterruptedException e) {
            }
        }
    }
}
