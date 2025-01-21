/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8313672
 * @summary Test CCP notification for value update of AndL through LShiftI and
 *          ConvI2L.
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions
 *                   -XX:RepeatCompilation=20 -XX:-TieredCompilation
 *                   -XX:+StressIGVN -Xcomp
 *                   -XX:CompileCommand=compileonly,compiler.ccp.TestShiftConvertAndNotification::test
 *                   compiler.ccp.TestShiftConvertAndNotification
 */

/*
 * @test
 * @bug 8313672
 * @summary Test CCP notification for value update of AndL through LShiftI and
 *          ConvI2L (no flags).
 * @run main compiler.ccp.TestShiftConvertAndNotification
 *
 */

package compiler.ccp;

public class TestShiftConvertAndNotification {
    static long instanceCount;
    static void test() {
        int i, i1 = 7;
        for (i = 7; i < 45; i++) {
            instanceCount = i;
            instanceCount &= i1 * i << i * Math.max(instanceCount, instanceCount);
            switch (i % 2) {
                case 8:
                    i1 = 0;
            }
        }
    }
    public static void main(String[] strArr) {
        for (int i = 0; i < 20_000; i++)
            test();
    }
}
