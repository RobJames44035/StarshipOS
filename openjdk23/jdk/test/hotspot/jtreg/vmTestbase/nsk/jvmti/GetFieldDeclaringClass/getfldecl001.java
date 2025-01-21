/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetFieldDeclaringClass;

import java.io.PrintStream;

public class getfldecl001 {
    final static int exit_delta = 95;

    static {
        try {
            System.loadLibrary("getfldecl001");
        } catch (UnsatisfiedLinkError err) {
            System.err.println("Could not load getfldecl001 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw err;
        }
    }

    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        System.exit(run(argv,System.out) + exit_delta);
    }

    native static void check(int i, Class cls1, Class cls2);
    native static int getResult();

    public static int run(String argv[], PrintStream out) {
        Class cls = getfldecl001c.class;

        check(0, cls, getfldecl001a.class);
        check(1, cls, getfldecl001b.class);
        check(2, cls, getfldecl001c.class);

        return getResult();
    }
}

interface getfldecl001a {
    int x = 0;

    public int x();
}

class getfldecl001b implements getfldecl001a {
    int y = 0;

    public int x() {
        return -1;
    }
}

class getfldecl001c extends getfldecl001b {
    int z = 0;

    public int y() {
        return -1;
    }
}
