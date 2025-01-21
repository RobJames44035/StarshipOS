/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetClassModifiers;

import java.io.PrintStream;

public class getclmdf005 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("getclmdf005");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load getclmdf005 library");
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
        check(0, byte.class);
        check(1, char.class);
        check(2, double.class);
        check(3, float.class);
        check(4, int.class);
        check(5, long.class);
        check(6, short.class);
        check(7, void.class);
        check(8, boolean.class);
        check(9, new byte[1].getClass());
        check(10, new char[1].getClass());
        check(11, new double[1].getClass());
        check(12, new float[1].getClass());
        check(13, new int[1].getClass());
        check(14, new long[1].getClass());
        check(15, new short[1].getClass());
        check(16, new boolean[1].getClass());
        return getRes();
    }
}
