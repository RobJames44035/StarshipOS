/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.IsMethodNative;

import java.io.PrintStream;

public class isnative001 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("isnative001");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load isnative001 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native void nmeth();
    native static int check();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        return check();
    }

    protected static final float[] meth_stat(int i, String s) {
        float[] f = new float[i];
        return f;
    }

    private char meth_1(char c1, char c2, char c3) {
        char loc1 = c1;
        return loc1;
    }

    class Inn {
        String fld;
        public synchronized final void meth_inn(String s, long l) {
            fld = s;
        }
    }
}
