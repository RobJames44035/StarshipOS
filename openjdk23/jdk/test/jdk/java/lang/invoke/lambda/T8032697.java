/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8032697
 * @summary Issues with Lambda
 */

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.LambdaConversionException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import T8032697_anotherpkg.T8032697_A;

public class T8032697 extends T8032697_A {

    interface I {
        int m();
    }

    interface IA {
        int m(T8032697_A x);
    }

    static MethodHandles.Lookup l;
    static MethodHandle h;
    private static MethodType mt(Class<?> k) { return MethodType.methodType(k); }
    private static MethodType mt(Class<?> k, Class<?> k2) { return MethodType.methodType(k, k2); }
    private static boolean mf(MethodType mti, MethodType mtf) {
        try {
            LambdaMetafactory.metafactory(l, "m", mti,mtf,h,mtf);
        } catch(LambdaConversionException e) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Throwable {
        l = MethodHandles.lookup();
        h = l.findVirtual(T8032697_A.class, "f", mt(int.class));
        if (mf(mt(I.class, T8032697.class), mt(int.class))) throw new AssertionError("Error: Should work");
        if (mf(mt(IA.class), mt(int.class, T8032697.class))) throw new AssertionError("Error: Should work");
        if (!mf(mt(I.class, T8032697_A.class), mt(int.class))) throw new AssertionError("Error: Should fail");
        if (!mf(mt(IA.class), mt(int.class, T8032697_A.class))) throw new AssertionError("Error: Should fail");
    }
}
