/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
* @test
* @bug 8286972
* @summary Test vectorization of loop induction variable usage in the loop
* @requires vm.compiler2.enabled
* @requires (os.simpleArch == "x64" & vm.cpu.features ~= ".*avx2.*") |
*           (os.simpleArch == "aarch64" & vm.cpu.features ~= ".*sve.*")
* @library /test/lib /
* @run driver compiler.vectorization.TestPopulateIndex
*/

package compiler.vectorization;
import compiler.lib.ir_framework.*;
import java.util.Random;

public class TestPopulateIndex {
    private static final int count = 10000;

    private int[] idx;
    private int[] src;
    private int[] dst;
    private float[] f;

    public static void main(String args[]) {
        TestFramework.run(TestPopulateIndex.class);
    }

    public TestPopulateIndex() {
        idx = new int[count];
        src = new int[count];
        dst = new int[count];
        f = new float[count];
        Random ran = new Random(0);
        for (int i = 0; i < count; i++) {
            src[i] = ran.nextInt();
        }
    }

    @Test
    @IR(counts = {IRNode.POPULATE_INDEX, "> 0"})
    public void indexArrayFill() {
        for (int i = 0; i < count; i++) {
            idx[i] = i;
        }
        checkResultIndexArrayFill();
    }

    public void checkResultIndexArrayFill() {
        for (int i = 0; i < count; i++) {
            int expected = i;
            if (idx[i] != expected) {
                throw new RuntimeException("Invalid result: idx[" + i + "] = " + idx[i] + " != " + expected);
            }
        }
    }

    @Test
    @IR(counts = {IRNode.POPULATE_INDEX, "> 0"})
    public void exprWithIndex1() {
        for (int i = 0; i < count; i++) {
            dst[i] = src[i] * (i & 7);
        }
        checkResultExprWithIndex1();
    }

    public void checkResultExprWithIndex1() {
        for (int i = 0; i < count; i++) {
            int expected = src[i] * (i & 7);
            if (dst[i] != expected) {
                throw new RuntimeException("Invalid result: dst[" + i + "] = " + dst[i] + " != " + expected);
            }
        }
    }

    @Test
    @IR(counts = {IRNode.POPULATE_INDEX, "> 0"})
    public void exprWithIndex2() {
        for (int i = 0; i < count; i++) {
            f[i] = i * i + 100;
        }
        checkResultExprWithIndex2();
    }

    public void checkResultExprWithIndex2() {
        for (int i = 0; i < count; i++) {
            float expected = i * i  + 100;
            if (f[i] != expected) {
                throw new RuntimeException("Invalid result: f[" + i + "] = " + f[i] + " != " + expected);
            }
        }
    }
}
