/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary Vectorization test on simple control flow in loop
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 *        compiler.vectorization.runner.VectorizationTestRunner
 *
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:.
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.vectorization.runner.LoopControlFlowTest
 *
 * @requires vm.compiler2.enabled
 */

package compiler.vectorization.runner;

import compiler.lib.ir_framework.*;

import java.util.Random;

public class LoopControlFlowTest extends VectorizationTestRunner {

    private static final int SIZE = 543;

    private int[] a;
    private int[] b;
    private boolean invCond;

    public LoopControlFlowTest() {
        a = new int[SIZE];
        b = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            a[i] = i + 80000;;
            b[i] = 80 * i;
        }
        Random ran = new Random(505050);
        invCond = (ran.nextInt() % 2 == 0);
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.STORE_VECTOR, ">0"})
    public int[] loopInvariantCondition() {
        int[] res = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            if (invCond) {
                res[i] = a[i] + b[i];
            } else {
                res[i] = a[i] - b[i];
            }
        }
        return res;
    }

    @Test
    public int[] arrayElementCondition() {
        int[] res = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            if (b[i] > 10000) {
                res[i] = a[i] + b[i];
            } else {
                res[i] = a[i] - b[i];
            }
        }
        return res;
    }

    @Test
    // Note that this loop cannot be vectorized due to early break.
    @IR(failOn = {IRNode.STORE_VECTOR})
    public int conditionalBreakReduction() {
        int sum = 0, i = 0;
        for (i = 0; i < SIZE; i++) {
            sum += i;
            if (invCond) break;
        }
        return i;
    }
}
