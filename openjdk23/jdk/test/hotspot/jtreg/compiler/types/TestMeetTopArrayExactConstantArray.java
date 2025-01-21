/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8027571
 * @summary meet of TopPTR exact array with constant array is not symmetric
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-UseOnStackReplacement
 *                   -XX:TypeProfileLevel=222 -XX:+UseTypeSpeculation
 *                   -XX:-BackgroundCompilation
 *                   compiler.types.TestMeetTopArrayExactConstantArray
 */

package compiler.types;

public class TestMeetTopArrayExactConstantArray {

    static class A {
    }

    static class B {
    }

    static class C extends A {
    }

    static class D extends C {
    }

    final static B[] b = new B[10];

    static void m0(Object[] o) {
        if (o.getClass() ==  Object[].class) {
        }
    }

    static void m1(Object[] o, boolean cond) {
        if (cond) {
            o = b;
        }
        m0(o);
    }

    static void m2(Object[] o, boolean cond1, boolean cond2) {
        if (cond1) {
            m1(o, cond2);
        }
    }

    static void m3(C[] o, boolean cond1, boolean cond2, boolean cond3) {
        if (cond1) {
            m2(o, cond2, cond3);
        }
    }

    static public void main(String[] args) {
        A[] a = new A[10];
        D[] d = new D[10];
        Object[] o = new Object[10];
        for (int i = 0; i < 5000; i++) {
            // record in profiling that the if in m0 succeeds
            m0(o);
            // record some profiling for m2 and m1
            m2(a, true, (i%2) == 0);
            // record some profiling for m3 and conflicting profile for m2
            m3(d, true, false, (i%2) == 0);
        }

        // get m3 compiled. The if in m0 will be optimized because of argument profiling in m3
        C[] c = new C[10];
        for (int i = 0; i < 20000; i++) {
            m3(c, true, false, (i%2) == 0);
        }
        // make m3 not entrant and the if in m0 fail
        m3(c, true, true, false);
        m3(c, true, true, false);
        m3(c, true, true, false);
        m3(c, true, true, false);

        // make m3 recompile, this time with if the not optimized
        // on entry to m3, argument o is of type C[], profiled C[]
        // on entry to m1, argument o is of type C[], speculative C[] exact, profiled A[]. Speculative becomes AnyNull
        // after the if in m1, speculative type of o becomes constant from final field b
        // the true if branch in m0 does a join between the type of o of speculative type constant from final field b and exact klass Object[]
        for (int i = 0; i < 20000; i++) {
            m3(c, true, false, (i%2) == 0);
        }

        System.out.println("TEST PASSED");
    }
}
