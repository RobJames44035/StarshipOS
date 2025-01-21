/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetFieldDeclaringClass;

import java.io.PrintStream;

public class getfldecl004 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("getfldecl004");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load getfldecl004 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void check(int i, Class cls1, Class cls2);
    native static int getRes();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        check(0, InnerClass2.class, InnerInterface1.class);
        check(1, InnerClass2.class, InnerInterface2.class);
        check(2, InnerClass2.class, InnerClass1.class);
        check(3, InnerClass2.class, InnerClass1.class);
        check(4, InnerClass2.class, InnerClass2.class);
        check(5, InnerClass2.class, InnerClass2.class);
        check(6, OuterClass2.class, OuterInterface1.class);
        check(7, OuterClass2.class, OuterInterface2.class);
        check(8, OuterClass2.class, OuterClass1.class);
        check(9, OuterClass2.class, OuterClass1.class);
        check(10, OuterClass2.class, OuterClass2.class);
        check(11, OuterClass2.class, OuterClass2.class);
        return getRes();
    }

    static interface InnerInterface1 {
        int staticField_ii1 = 0;
    }

    static interface InnerInterface2 extends InnerInterface1 {
        int staticField_ii2 = 1;
    }

    static abstract class InnerClass1 implements InnerInterface2 {
        static int staticField_ic1 = 2;
        int instanceField_ic1 = 3;
    }

    static class InnerClass2 extends InnerClass1 {
        static int staticField_ic2 = 4;
        int instanceField_ic2 = 5;
    }
}

interface OuterInterface1 {
    int staticField_oi1 = 6;
}

interface OuterInterface2 extends OuterInterface1 {
    int staticField_oi2 = 7;
}

abstract class OuterClass1 implements OuterInterface2 {
    static int staticField_oc1 = 8;
    int instanceField_oc1 = 9;
}

class OuterClass2 extends OuterClass1 {
    static int staticField_oc2 = 10;
    int instanceField_oc2 = 11;
}
