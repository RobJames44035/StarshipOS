/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8303511
 * @summary C2: assert(get_ctrl(n) == cle_out) during unrolling
 * @requires vm.gc.Parallel
 * @run main/othervm -XX:-BackgroundCompilation -XX:+UseParallelGC TestAddPAtOuterLoopHead
 */


import java.util.Arrays;

public class TestAddPAtOuterLoopHead {
    public static void main(String[] args) {
        boolean[] flags1 = new boolean[1000];
        boolean[] flags2 = new boolean[1000];
        Arrays.fill(flags2, true);
        for (int i = 0; i < 20_000; i++) {
            testHelper(42, 42, 43);
            test(flags1);
            test(flags2);
        }
    }

    private static int test(boolean[] flags) {
        int[] array = new int[1000];

        int k;
        for (k = 0; k < 10; k++) {
            for (int i = 0; i < 2; i++) {
            }
        }
        k = k / 10;
        int m;
        for (m = 0; m < 2; m++) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                }
            }
        }


        int v = 0;
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 998; i += k) {
                int l = testHelper(m, j, i);
                v = array[i + l];
                if (flags[i]) {
                    return v;
                }
            }
        }

        return v;
    }

    private static int testHelper(int m, int j, int i) {
        return m == 2 ? j : i;
    }
}
