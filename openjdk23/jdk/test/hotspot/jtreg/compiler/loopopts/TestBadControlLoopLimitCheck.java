/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8245714
 * @requires vm.compiler2.enabled
 * @summary "Bad graph detected in build_loop_late" when loads are pinned on loop limit check uncommon branch
 *
 * @run main/othervm -XX:-BackgroundCompilation -XX:ArrayCopyLoadStoreMaxElem=0 TestBadControlLoopLimitCheck
 */

public class TestBadControlLoopLimitCheck {
    public static void main(String[] args) {
        int[] int_array = {0, 0};
        A[] obj_array = {new A(), new A()};
        for (int i = 0; i < 20_000; i++) {
            test1(int_array, 0, 10, false);
            test_helper(42);
            test2(obj_array, 0, 10, false);
        }
    }

    private static int test1(int[] a, int start, int stop, boolean flag) {
        int[] b = new int[2]; // non escaping allocation
        System.arraycopy(a, 0, b, 0, 2); // optimized out
        int v = 1;
        int j = 0;
        for (; j < 10; j++);
        int inc = test_helper(j); // delay transformation to counted loop
        // loop limit check here has loads pinned on unc branch
        for (int i = start; i < stop; i += inc) {
            v *= 2;
        }
        if (flag) {
            v += b[0] + b[1];
        }
        return v;
    }

    private static int test2(A[] a, int start, int stop, boolean flag) {
        A[] b = new A[2]; // non escaping allocation
        System.arraycopy(a, 0, b, 0, 2); // optimized out
        int v = 1;
        int j = 0;
        for (; j < 10; j++);
        int inc = test_helper(j); // delay transformation to counted loop
        // loop limit check here has loads pinned on unc branch
        for (int i = start; i < stop; i += inc) {
            v *= 2;
        }
        if (flag) {
            v += b[0].f + b[1].f;
        }
        return v;
    }

    static class A {
        int f;
    }

    static int test_helper(int j) {
        return j == 10 ? 10 : 1;
    }
}
