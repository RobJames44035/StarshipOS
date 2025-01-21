/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8027422
 * @summary type methods shouldn't always operate on speculative part
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:TypeProfileLevel=222
 *                   -XX:+UseTypeSpeculation -XX:-BackgroundCompilation
 *                   compiler.types.TestSpeculationFailedHigherEqual
 */

package compiler.types;

public class TestSpeculationFailedHigherEqual {

    static class A {
        void m() {}
        int i;
    }

    static class C extends A {
    }

    static C c;

    static A m1(A a, boolean cond) {
        // speculative type for a is C not null
        if (cond ) {
            a = c;
        }
        // speculative type for a is C (may be null)
        int i = a.i;
        return a;
    }

    static public void main(String[] args) {
        C c = new C();
        TestSpeculationFailedHigherEqual.c = c;
        for (int i = 0; i < 20000; i++) {
            m1(c, i%2 == 0);
        }

        System.out.println("TEST PASSED");
    }
}
