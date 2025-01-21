/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8340214
 * @summary C2 compilation asserts with "no node with a side effect" in PhaseIdealLoop::try_sink_out_of_loop
 *
 * @run main/othervm -XX:-BackgroundCompilation TestBadMemSliceWithInterfaces
 *
 */

public class TestBadMemSliceWithInterfaces {
    public static void main(String[] args) {
        B b = new B();
        C c = new C();
        for (int i = 0; i < 20_000; i++) {
            test1(b, c, true);
            test1(b, c, false);
            b.field = 0;
            c.field = 0;
            int res = test2(b, c, true);
            if (res != 42) {
                throw new RuntimeException("incorrect result " + res);
            }
            res = test2(b, c, false);
            if (res != 42) {
                throw new RuntimeException("incorrect result " + res);
            }
        }
    }

    private static void test1(B b, C c, boolean flag) {
        A a;
        if (flag) {
            a = b;
        } else {
            a = c;
        }
        for (int i = 0; i < 1000; i++) {
            a.field = 42;
        }
    }

    private static int test2(B b, C c, boolean flag) {
        A a;
        if (flag) {
            a = b;
        } else {
            a = c;
        }
        int v = 0;
        for (int i = 0; i < 2; i++) {
            v += a.field;
            a.field = 42;
        }
        return v;
    }

    interface I {
        void m();
    }

    static class A {
        int field;
    }

    static class B extends A implements I {
        @Override
        public void m() {

        }
    }

    static class C extends A implements I {
        @Override
        public void m() {

        }
    }
}
