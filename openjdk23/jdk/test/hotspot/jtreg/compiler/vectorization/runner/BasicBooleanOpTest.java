/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @summary Vectorization test on basic boolean operations
 * @requires vm.opt.StressUnstableIfTraps == null | !vm.opt.StressUnstableIfTraps
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 *        compiler.vectorization.runner.VectorizationTestRunner
 *
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:.
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.vectorization.runner.BasicBooleanOpTest
 *
 * @requires vm.compiler2.enabled
 */

package compiler.vectorization.runner;

import compiler.lib.ir_framework.*;

public class BasicBooleanOpTest extends VectorizationTestRunner {

    private static final int SIZE = 6543;

    private boolean[] a;
    private boolean[] b;
    private boolean[] c;

    public BasicBooleanOpTest() {
        a = new boolean[SIZE];
        b = new boolean[SIZE];
        c = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            a[i] = true;
            b[i] = false;
        }
    }

    // ---------------- Logic ----------------
    @Test
    public boolean[] vectorNot() {
        boolean[] res = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = !a[i];
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        phase = CompilePhase.BEFORE_MACRO_EXPANSION,
        counts = {IRNode.AND_VB, ">0"})
    public boolean[] vectorAnd() {
        boolean[] res = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = a[i] & b[i];
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.OR_VB, ">0"})
    public boolean[] vectorOr() {
        boolean[] res = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = a[i] | b[i];
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.XOR_VB, ">0"})
    public boolean[] vectorXor() {
        boolean[] res = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = a[i] ^ b[i];
        }
        return res;
    }
}
