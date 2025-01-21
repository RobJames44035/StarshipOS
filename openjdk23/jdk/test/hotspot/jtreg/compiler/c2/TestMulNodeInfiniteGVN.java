/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 * @bug 8291466
 * @summary Infinite loop in PhaseIterGVN::transform_old with -XX:+StressIGVN
 * @requires vm.compiler2.enabled
 * @run main/othervm -Xbatch -XX:-TieredCompilation
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN
 *                   -XX:StressSeed=1 compiler.c2.TestMulNodeInfiniteGVN
 */

package compiler.c2;

public class TestMulNodeInfiniteGVN {

    private static int fun() {
        int sum = 0;
        for (int c = 0; c < 50000; c++) {
            int x = 9;
            while ((x += 2) < 12) {
                for (int k = 1; k < 2; k++) {
                    sum += x * k;
                }
            }
            int y = 11;
            while ((y += 2) < 14) {
                for (int k = 1; k < 2; k++) {
                    sum += y * k;
                }
            }
            int z = 17;
            while ((z += 2) < 20) {
                for (int k = 1; k < 2; k++) {
                    sum += z * k;
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        fun();
    }
}
