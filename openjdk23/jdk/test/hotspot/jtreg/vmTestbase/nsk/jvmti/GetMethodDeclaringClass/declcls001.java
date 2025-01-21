/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetMethodDeclaringClass;

import java.io.PrintStream;

public class declcls001 {

    static {
        try {
            System.loadLibrary("declcls001");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load declcls001 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int check();

    static int ifld;

    public static void main(String[] args) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        System.exit(run(args, System.out) + 95/*STATUS_TEMP*/);
    }

    public static int run(String argv[], PrintStream ref) {
        return check();
    }

    static void meth(int i) {
        ifld = i;
    }

    class Inn {
        String fld;
        void meth_inn(String s) {
            fld = s;
        }
    }
}

class declcls001a extends declcls001 {
}

class declcls001b extends declcls001a {
    public void meth_b() {
    }
}

class declcls001z {
    int meth_z() {
        return 100;
    }
}

interface declcls001i {
    int meth_i();
}

interface declcls001i1 extends declcls001i {
    int meth_i1();
}

abstract class declcls001i_a extends declcls001z implements declcls001i1 {
    public int meth_i1() {
        return 1;
    }
}
