/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8131969
 * @summary assert in register allocation code when vector Phi for a loop is
 * processed because code assumes all inputs already processed
 *
 * @run main/othervm -Xbatch compiler.regalloc.TestVectorRegAlloc
 */

package compiler.regalloc;

public class TestVectorRegAlloc {

    static int test_helper_i;
    static boolean test_helper() {
        test_helper_i++;
        return (test_helper_i & 7) != 0;
    }

    static void test(double[] src, double[] dst, boolean flag) {
        double j = 0.0;
        while(test_helper()) {
            for (int i = 0; i < src.length; i++) {
                dst[i] = src[i] + j;
            }
            // Loop will be unswitched and ReplicateD of zero will be
            // split through the Phi of outer loop
            for (int i = 0; i < src.length; i++) {
                double k;
                if (flag) {
                    k = j;
                } else {
                    k = 0;
                }
                dst[i] = src[i] + k;
            }
            j++;
        }
    }

    static public void main(String[] args) {
        double[] src = new double[10];
        double[] dst = new double[10];
        for (int i = 0; i < 20000; i++) {
            test(src, dst, (i % 2) == 0);
        }
    }
}
