/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8244663
 * @summary Shenandoah: C2 assertion fails in Matcher::collect_null_checks
 * @requires vm.flavor == "server"
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:-UseOnStackReplacement
 *                   -XX:CompileCommand=dontinline,TestShenandoahCmpPAfterCall::not_inlined TestShenandoahCmpPAfterCall
 *
 */

public class TestShenandoahCmpPAfterCall {
    private static Object field1 = new Object();
    private static Object field2 = new Object();
    private static Object o3;
    private static volatile int barrier;

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test();
        }
    }

    private static void test() {
        Object o1 = null;
        Object o2 = field2;
        try {
            not_inlined();
            o1 = field1;
            if (o1 == o2) {

            }
        } catch (Exception1 ex1) {
            o1 = field1;
            if (o1 == o2) {

            }
        }
        barrier = 42;
        if (o1 == o2) {

        }
    }

    static int count = 0;
    private static void not_inlined() throws Exception1 {
        count++;
        if ((count % 100) == 0) {
            throw new Exception1();
        }
    }

    private static class Exception1 extends Exception {
    }
}
