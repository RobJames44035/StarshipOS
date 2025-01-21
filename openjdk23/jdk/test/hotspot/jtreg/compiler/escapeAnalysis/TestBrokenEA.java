/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8285835
 * @summary EA does not propagate NSR (not scalar replaceable) state.
 * @run main/othervm -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:-UseOnStackReplacement TestBrokenEA
 */

public class TestBrokenEA {

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test1(true);
            test1(false);
            test2(true);
            test2(false);
        }
    }

    private static void test1(boolean flag) {
        A[] array = new A[1];
        if (flag) {
            C c = new C();
            B b = new B();
            b.c = c;
            A a = new A();
            a.b = b;
            array[0] = a;
        }
        A a = array[0];
        if (a != null) {
            a.b.c.f = 0x42;
        }
    }

    private static void test2(boolean flag) {
        A a = null;
        if (flag) {
            C c = new C();
            B b = new B();
            b.c = c;
            a = new A();
            a.b = b;
        }
        if (a != null) {
            a.b.c.f = 0x42;
        }
    }

    private static class A {
        public B b;
    }

    private static class B {
        public C c;
    }

    private static class C {
        public int f;
    }
}
