/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.ClassFileLoadHook;

/** Tested class with dummy bytecode to be loaded in JVMTI tests. */
public class classfloadhk002r {

    public static int dummyStaticInt = 0;

    static {
        dummyStaticInt = 2;
    }

    private double dummyDouble = 0.0;

    public classfloadhk002r(double d) {
        dummyDouble = d;
    }

    public double dummyMethod(int n) throws RuntimeException {
        double s = 0;

        try {
            if (n <= 0)
                return 0.0;
            s += dummyMethod((n - 1) * dummyStaticInt) * dummyDouble;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return s;
    }
}
