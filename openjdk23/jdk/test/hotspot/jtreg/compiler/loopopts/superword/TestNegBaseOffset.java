/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8202948
 * @summary Test skipping vector packs with negative base offset.
 * @comment Test fails only with -Xcomp when profiling data is not present.
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+UnlockExperimentalVMOptions
 *                   -Xcomp -XX:-TieredCompilation -XX:CICompilerCount=1
 *                   -XX:CompileOnly=compiler.loopopts.superword.TestNegBaseOffset::*
 *                   compiler.loopopts.superword.TestNegBaseOffset
 */

package compiler.loopopts.superword;

public class TestNegBaseOffset {
    public static final int N = 400;
    public static int iFld=10;
    public static int iArr[]=new int[N];

    public static void mainTest() {
        int i0=1, i2;
        while (++i0 < 339) {
            if ((i0 % 2) == 0) {
                for (i2 = 2; i2 > i0; i2 -= 3) {
                    iArr[i2 - 1] &= iFld;
                }
            }
        }
    }

    public static void main(String[] strArr) {
        for (int i = 0; i < 10; i++) {
            mainTest();
        }
    }
}
