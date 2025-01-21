/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.ClassFileLoadHook;

/** Redefined tested class with new methods implementation. */
public class classfloadhk009r {
    static long staticField = 0;

    static {
        staticField = 2;
    }

    int intField;

    public classfloadhk009r(int n) {
        intField = n;
    }

    public long longMethod(int i) {
        return (i + intField) * 2;
    }

    // expected to return 58
    public static long testedStaticMethod() {
        classfloadhk009r obj = new classfloadhk009r(10);
        return obj.longMethod(20) - staticField;
    }
}
