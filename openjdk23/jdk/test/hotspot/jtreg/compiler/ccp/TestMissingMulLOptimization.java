/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8299546
 * @summary Tests that MulL::Value() does not return bottom type and then an optimized type again in CCP.
 * @run main/othervm -Xcomp -XX:CompileCommand=compileonly,compiler.ccp.TestMissingMulLOptimization::*
 *                   -XX:CompileCommand=dontinline,compiler.ccp.TestMissingMulLOptimization::*
 *                   compiler.ccp.TestMissingMulLOptimization
 */
package compiler.ccp;

public class TestMissingMulLOptimization {
    static int N;
    static long x;

    public static void main(String[] strArr) {
        try {
            test();
        } catch (RuntimeException e) {
            // Expected
        }
    }

    static int test() {
        int i6 = 2, i10 = 3, i11, iArr[] = new int[N];
        long l = 3151638515L;
        double dArr[] = new double[N];
        dontInline();
        int i;
        for (i = 7; i < 221; i++) {
            i6 *= i6;
        }
        for (int j = 9; 83 > j; ) {
            for (i11 = 1; i11 < 6; ++i11) {
                l *= i;
                l += 3;
            }
        }
        x += i6;
        return 34;
    }

    static int dontInline() {
        throw new RuntimeException();
    }
}
