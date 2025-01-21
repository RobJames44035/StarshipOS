/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.ClassFileLoadHook;

/** Tested class to be loaded in JVMTI tests. */
public class classfloadhk006r {
    static long staticField = 0;

    static {
        staticField = 2;
    }

    int intField;

    public classfloadhk006r(int n) {
        intField = n;
    }

    public long longMethod(int i) {
        return (i + intField);
    }

    // expected to return 20
    public static long testedStaticMethod() {
        classfloadhk006r obj = new classfloadhk006r(6);
        return obj.longMethod(4) * staticField;
    }
}
