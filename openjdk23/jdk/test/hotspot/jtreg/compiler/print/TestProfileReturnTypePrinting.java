/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8073154
 * @run main/othervm -XX:TypeProfileLevel=020
 *                   -XX:CompileCommand=compileonly,compiler.print.TestProfileReturnTypePrinting::testMethod
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:+PrintLIR
 *                   compiler.print.TestProfileReturnTypePrinting
 * @summary Verify that c1's LIR that contains ProfileType node could be dumped
 *          without a crash disregard to an exact class knowledge.
 */

package compiler.print;

public class TestProfileReturnTypePrinting {
    private static final int ITERATIONS = 1_000_000;

    public static void main(String args[]) {
        for (int i = 0; i < ITERATIONS; i++) {
            TestProfileReturnTypePrinting.testMethod(i);
        }
    }

    private static int testMethod(int i) {
        return TestProfileReturnTypePrinting.foo().hashCode()
                + TestProfileReturnTypePrinting.bar(i).hashCode();
    }

    /* Exact class of returned value is known statically. */
    private static B foo() {
        return new B();
    }

    /* Exact class of returned value is not known statically. */
    private static Object bar(int i) {
        if (i % 2 == 0) {
            return new A();
        } else {
            return new B();
        }
    }

    private static class A {
    }

    private static class B extends A {
    }
}
