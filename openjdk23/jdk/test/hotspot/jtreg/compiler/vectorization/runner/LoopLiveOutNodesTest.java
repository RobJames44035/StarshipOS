/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @summary Vectorization test on loops with live out nodes
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 *        compiler.vectorization.runner.VectorizationTestRunner
 *
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:.
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.vectorization.runner.LoopLiveOutNodesTest
 *
 * @requires vm.compiler2.enabled
 */

package compiler.vectorization.runner;

import compiler.lib.ir_framework.*;

import java.util.Random;

public class LoopLiveOutNodesTest extends VectorizationTestRunner {

    private static final int SIZE = 543;

    private int[] a;
    private int start;
    private int limit;

    // tmp[] may be modified and thus should not be returned in cases.
    private int[] tmp;

    public LoopLiveOutNodesTest() {
        a = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            a[i] = -697989 * i;
        }
        tmp = new int[SIZE];
        Random ran = new Random(31415926);
        start = ran.nextInt() % 100;
        limit = start + 235;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse4.1", "true"},
        counts = {IRNode.STORE_VECTOR, ">0"})
    public int SimpleIvUsed() {
        int i = 0;
        int[] res = new int[SIZE];
        for (i = start; i < limit; i++) {
            res[i] = a[i] * 2757;
        }
        return i;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.STORE_VECTOR, ">0"})
    public int indexedByIvUsed() {
        int i = 0;
        int[] res = new int[SIZE];
        for (i = start; i < limit; i++) {
            res[i] = a[i] & 0x77ff77ff;
        }
        return a[i - 1];
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.STORE_VECTOR, ">0"})
    public int ivUsedMultiple() {
        int i = 0;
        int[] res = new int[SIZE];
        for (i = start; i < limit; i++) {
            res[i] = a[i] | 65535;
        }
        return i * i;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.STORE_VECTOR, ">0"})
    public int ivUsedComplexExpr() {
        int i = 0;
        int[] res = new int[SIZE];
        for (i = start; i < limit; i++) {
            res[i] = a[i] - 100550;
        }
        return a[i] + a[i - 2] + i * i;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.STORE_VECTOR, ">0"})
    public int[] ivUsedAnotherLoop() {
        int i = 0;
        int[] res = new int[SIZE];
        for (i = start; i < limit; i++) {
            res[i] = a[i] * 100;
        }
        for (int j = i; j < i + 55; j++) {
            res[j] = a[j] + 2323;
        }
        return res;
    }

    @Test
    public int ivUsedInParallel() {
        int i = 0, j = 0;
        int[] res = new int[SIZE];
        for (i = start; i < limit; i++, j++) {
            res[i] = a[i] + i;
        }
        return i * j + a[i] * a[j];
    }

    @Test
    public int valueLiveOut() {
        int val = 0;
        int[] res = new int[SIZE];
        for (int i = start; i < limit; i++) {
            val = a[i] - 101;
            res[i] = val;
        }
        return val;
    }

    @Test
    public int nestedLoopIndexLiveOut() {
        int k = 0;
        for (int i = 0; i < 50; i += 2) {
            for (int j = 0; j < 10; j++) {
                tmp[k++] = 5;
            }
        }
        return k;
    }
}
