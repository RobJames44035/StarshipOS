/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8214344
 * @summary LoadVector from a known element of a stable array shouldn't attempt to constant fold
 * @modules java.base/jdk.internal.vm.annotation
 *
 * @run main/bootclasspath/othervm -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:-UseOnStackReplacement LoadVectorFromStableArray
 *
 */

import jdk.internal.vm.annotation.Stable;

public class LoadVectorFromStableArray {
    public static void main(String[] args) {
        byte[] byte_array = new byte[100];
        for (int i = 0; i < 20_000; i++) {
            test_helper(0, 0);
            test_helper(42, 0);
            test_helper2(0, 20, byte_array, byte_array);
        }
        for (int i = 0; i < 20_000; i++) {
            test(20, true);
            test(20, false);
        }
    }

    static @Stable byte[] stable_array1 = new byte[100];
    static @Stable byte[] stable_array2 = new byte[100];

    private static void test(int stop, boolean flag) {
        int start = 0;
        byte[] byte_array1 = stable_array1;
        byte[] byte_array2 = flag ? stable_array1 : stable_array2;
        int k = 2;
        for (; k < 4; k *= 2);
        int i = test_helper(k, stop);
        // Loop in this method is unrolled and vectorized, then, upper
        // bound is found to be constant and small. A single iteration
        // of the main loop is executed. That iteration attempts to
        // load a vector element from the array at a constant offset.
        test_helper2(start, i, byte_array1, byte_array2);
    }


    private static void test_helper2(int start, int stop, byte[] byte_array1, byte[] byte_array2) {
        for (int j = start; j < stop; j++) {
            byte b = byte_array1[j+3];
            byte_array2[j+3] = b;
        }
    }

    private static int test_helper(int k, int stop) {
        int i = 0;
        if (k == 42) {
            i = stop;
        } else {
            i = 5;
        }
        return i;
    }
}
