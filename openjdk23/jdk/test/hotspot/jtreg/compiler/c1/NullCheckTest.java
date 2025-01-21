/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 6478991
 * @summary C1 NullCheckEliminator yields incorrect exceptions
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.c1.NullCheckTest::test
 *      -XX:CompileCommand=compileonly,compiler.c1.NullCheckTest::inlined
 *      compiler.c1.NullCheckTest
 */

package compiler.c1;

public class NullCheckTest {
    static class A {
        int f;

        public final void inlined(A a) {
            // This cast is intended to fail.
            B b = ((B) a);
        }
    }

    static class B extends A {
    }


    private static void test(A a1, A a2) {
        // Inlined call must do a null check on a1.
        // However, the exlipcit NullCheck instruction is eliminated and
        // the null check is folded into the field load below, so the
        // exception in the inlined method is thrown before the null check
        // and the NullPointerException is not thrown.
        a1.inlined(a2);

        int x = a1.f;
    }

    public static void main(String[] args) {
        // load classes
        new B();
        try {
                test(null, new A());

                throw new InternalError("FAILURE: no exception");
        } catch (NullPointerException ex) {
                System.out.println("CORRECT: NullPointerException");
        } catch (ClassCastException ex) {
                System.out.println("FAILURE: ClassCastException");
                throw ex;
        }
    }
}
