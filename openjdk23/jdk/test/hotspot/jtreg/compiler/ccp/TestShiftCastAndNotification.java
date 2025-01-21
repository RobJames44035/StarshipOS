/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8306042
 * @summary CCP missed optimization opportunity. Due to missing notification through Casts.
 * @library /test/lib
 * @run main/othervm -Xcomp -XX:-TieredCompilation
 *                   -XX:CompileOnly=compiler.ccp.TestShiftCastAndNotification::test
 *                   compiler.ccp.TestShiftCastAndNotification
 */

package compiler.ccp;

public class TestShiftCastAndNotification {
    static int N;
    static int iArrFld[] = new int[1];
    static int test() {
        int x = 1;
        int sval = 4;
        long useless[] = new long[N];
        for (double d1 = 63; d1 > 2; d1 -= 2) {
            for (double d2 = 3; 1 < d2; d2--) {
                x <<= sval; // The LShiftI
            }
            // CastII probably somewhere in the loop structure
            x &= 3; // The AndI
            for (int i = 1; i < 3; i++) {
                try {
                    x = iArrFld[0];
                    sval = 0;
                } catch (ArithmeticException a_e) {
                }
            }
        }
        return x;
    }
    public static void main(String[] args) {
        test();
    }
}
