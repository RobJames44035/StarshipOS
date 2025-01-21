/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;
import jdk.test.whitebox.WhiteBox;

/*
 * @test
 * @bug 8283187
 * @summary C2: loop candidate for superword not always unrolled fully if superword fails
 * @library /test/lib /
 * @build jdk.test.whitebox.WhiteBox
 * @requires vm.compiler2.enabled
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI compiler.c2.irTests.TestSuperwordFailsUnrolling
 */

public class TestSuperwordFailsUnrolling {
    private static int v = 0;
    private final static WhiteBox wb = WhiteBox.getWhiteBox();

    public static void main(String[] args) {
        Object avx = wb.getVMFlag("UseAVX");
        if (avx != null && ((Long)avx) > 2) {
            TestFramework.runWithFlags("-XX:UseAVX=2", "-XX:LoopMaxUnroll=8", "-XX:-SuperWordReductions");
        }
        TestFramework.runWithFlags("-XX:LoopMaxUnroll=8", "-XX:-SuperWordReductions");
    }

    @Test
    @IR(applyIf = { "UsePopCountInstruction", "true" }, counts = { IRNode.POPCOUNT_L, ">=10" })
    private static int test(long[] array1, long[] array2) {
        v = 0;
        for (int i = 0; i < array1.length; i++) {
            v += Long.bitCount(array1[i]);
        }
        return v;
    }

    @Run(test = "test")
    void test_runner() {
        long[] array = new long[1000];
        test(array, array);
    }
}
