/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetVersionNumber;

import java.io.PrintStream;

public class getvern001 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("getvern001");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load getvern001 library");
            System.err.println("java.library.path: "
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int check();

    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String argv[],PrintStream out) {
        return check();
    }
}
