/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary Vectorization test with small strip mining iterations
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 *        compiler.vectorization.runner.VectorizationTestRunner
 *
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:.
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:LoopStripMiningIter=10
 *                   compiler.vectorization.runner.StripMinedLoopTest
 *
 * @requires vm.compiler2.enabled
 */

package compiler.vectorization.runner;

import compiler.lib.ir_framework.*;

import java.util.Random;

public class StripMinedLoopTest extends VectorizationTestRunner {

    private static final int SIZE = 543;

    private int[] a = new int[SIZE];
    private int[] b = new int[SIZE];

    public StripMinedLoopTest() {
        for (int i = 0; i < SIZE; i++) {
            a[i] = 2;
            b[i] = 3;
        }
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.STORE_VECTOR, ">0"})
    public int[] stripMinedVectorLoop() {
        int[] res = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = a[i] + b[i];
        }
        return res;
    }

    @Test
    public int stripMinedReductionLoop() {
        int res = 0;
        for (int i = 0; i < SIZE; i++) {
            res += a[i];
        }
        return res;
    }

    @Test
    public int stripMinedOneIterationLoop() {
        int[] res = new int[SIZE];
        int i1, i2, i3, i4 = 11937;
        for (i1 = 1; i1 < SIZE; i1++) {
            for (i2 = 1; i2 < 2; i2++) {
                for (i3 = 1; i3 < 2; i3++) {
                    i4 &= i3;
                }
            }
            res[i1] = 0;
        }
        return res[0] + i4;
    }
}
