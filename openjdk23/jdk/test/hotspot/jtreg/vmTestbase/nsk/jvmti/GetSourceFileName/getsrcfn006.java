/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetSourceFileName;

import java.io.PrintStream;

public class getsrcfn006 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("getsrcfn006");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load getsrcfn006 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void check(int i, Class cls);
    native static int getRes();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        check(0, getsrcfn006.class);
        check(1, getsrcfn006a.class);
        check(2, getsrcfn006b.class);
        check(3, getsrcfn006c.class);
        return getRes();
    }
}
