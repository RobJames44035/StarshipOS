/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8290711
 * @summary assert(false) failed: infinite loop in PhaseIterGVN::optimize
 * @run main/othervm -XX:-BackgroundCompilation -XX:-UseOnStackReplacement -XX:-TieredCompilation TestInfiniteIGVNAfterCCP
 */


import java.util.function.BooleanSupplier;

public class TestInfiniteIGVNAfterCCP {
    private static int inc;
    private static volatile boolean barrier;

    static class A {
        int field1;
        int field2;
    }

    public static void main(String[] args) {
        A a = new A();
        for (int i = 0; i < 20_000; i++) {
            test(false, a, false);
            inc = 0;
            testHelper(true, () -> inc < 10, a, 4, true);
            inc = 0;
            testHelper(true, () -> inc < 10, a, 4, false);
            testHelper(false, () -> inc < 10, a, 42, false);
        }
    }

    private static void test(boolean flag2, A a, boolean flag1) {
        int i = 2;
        for (; i < 4; i *= 2);
        testHelper(flag2, () -> true, a, i, flag1);
    }

    private static void testHelper(boolean flag2, BooleanSupplier f, A a, int i, boolean flag1) {
        if (i == 4) {
            if (a == null) {

            }
        } else {
            a = null;
        }
        if (flag2) {
            while (true) {
                synchronized (new Object()) {

                }
                if (!f.getAsBoolean()) {
                    break;
                }
                if (flag1) {
                    if (a == null) {

                    }
                }
                barrier = true;
                inc++;
                if (inc % 2 == 0) {
                    a.field1++;
                }
            }
        }
    }
}
