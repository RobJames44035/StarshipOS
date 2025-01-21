/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8258393 8263376
 * @summary Shenandoah: "graph should be schedulable" assert failure
 * @requires vm.flavor == "server"
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UseShenandoahGC -XX:-BackgroundCompilation TestBadRawMemoryAfterCall
 *
 */

public class TestBadRawMemoryAfterCall {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        C c = new C();
        for (int i = 0; i < 20_000; i++) {
            test1(a);
            test1(b);
            test1(c);

            test2(a, i);
            test2(b, i);
            test2(c, i);
        }
    }

    private static Object test1(A a) {
        if (a.getClass() == A.class) {
        }

        Object o = null;
        try {
            a.m();
            o = a.getClass();
        } catch (Exception e) {

        }
        return o;
    }

    static int field;

    private static Object test2(A a, int i) {
        if (a.getClass() == A.class) {
        }

        Object o = null;
        try {
            a.m();
            o = a.getClass();
        } catch (Exception e) {
            i = 42;
        }
        if (i == 42) {
            field = 42;
        }
        return o;
    }

    private static class A {
        void m() throws Exception {
            throw new Exception();
        }
    }
    private static class B extends A {
        void m() {
        }
    }
    private static class C extends B {
        void m() {
        }
    }
}
