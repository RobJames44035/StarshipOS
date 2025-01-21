/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8299959
 * @summary In CmpU::Value, the sub computation may be narrower than the overflow computation.
 * @requires vm.compiler2.enabled
 *
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressCCP -Xcomp -XX:-TieredCompilation
 *                   -XX:CompileCommand=compileonly,compiler.rangechecks.TestRangeCheckCmpUOverflowVsSub::test
 *                   -XX:RepeatCompilation=50
 *                   compiler.rangechecks.TestRangeCheckCmpUOverflowVsSub
*/

package compiler.rangechecks;

public class TestRangeCheckCmpUOverflowVsSub {
    static int arr[] = new int[400];

    public static void main(String[] strArr) {
        for (int i = 0; i < 10; i++) {
            test(); // repeat for multiple compilations
        }
    }

    static void test() {
        for(int i = 0; i < 50_000; i++) {} //empty loop - trigger OSR faster
        int val;
        int zero = arr[5];
        int i = 1;
        do {
            for (int j = 1; j < 3; j++) {
                for (int k = 2; k > i; k -= 3) {
                    try {
                        val = arr[i + 1] % k;
                        val = arr[i - 1] % zero;
                        val = arr[k - 1];
                    } catch (ArithmeticException e) {} // catch div by zero
                }
            }
        } while (++i < 3);
    }
}

