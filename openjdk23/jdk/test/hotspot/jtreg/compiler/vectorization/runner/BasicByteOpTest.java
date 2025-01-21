/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @summary Vectorization test on basic byte operations
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 *        compiler.vectorization.runner.VectorizationTestRunner
 *
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:.
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   -XX:CompileCommand=CompileOnly,compiler.vectorization.runner.BasicByteOpTest::*
 *                   -XX:LoopUnrollLimit=1000
 *                   compiler.vectorization.runner.BasicByteOpTest
 *
 * @requires vm.compiler2.enabled
 */

package compiler.vectorization.runner;

import compiler.lib.ir_framework.*;

public class BasicByteOpTest extends VectorizationTestRunner {

    private static int SIZE = 6543;

    private byte[] a;
    private byte[] b;
    private byte[] c;

    public BasicByteOpTest() {
        a = new byte[SIZE];
        b = new byte[SIZE];
        c = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            a[i] = (byte) (-3 * i);
            b[i] = (byte) (i + 4);
            c[i] = (byte) -90;
        }
    }

    // ---------------- Arithmetic ----------------
    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.SUB_VB, ">0"})
    public byte[] vectorNeg() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) -a[i];
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "ssse3", "true"},
        counts = {IRNode.ABS_VB, ">0"})
    public byte[] vectorAbs() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) Math.abs(a[i]);
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.ADD_VB, ">0"})
    public byte[] vectorAdd() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (a[i] + b[i]);
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.SUB_VB, ">0"})
    public byte[] vectorSub() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (a[i] - b[i]);
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse4.1", "true"},
        counts = {IRNode.MUL_VB, ">0"})
    public byte[] vectorMul() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (a[i] * b[i]);
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse4.1", "true"},
        counts = {IRNode.MUL_VB, ">0", IRNode.ADD_VB, ">0"})
    public byte[] vectorMulAdd() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (c[i] + a[i] * b[i]);
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse4.1", "true"},
        counts = {IRNode.MUL_VB, ">0", IRNode.SUB_VB, ">0"})
    public byte[] vectorMulSub() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (c[i] - a[i] * b[i]);
        }
        return res;
    }

    // ---------------- Logic ----------------
    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.XOR_VB, ">0"})
    public byte[] vectorNot() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) ~a[i];
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.AND_VB, ">0"})
    public byte[] vectorAnd() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (a[i] & b[i]);
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.OR_VB, ">0"})
    public byte[] vectorOr() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (a[i] | b[i]);
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse2", "true"},
        counts = {IRNode.XOR_VB, ">0"})
    public byte[] vectorXor() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (a[i] ^ b[i]);
        }
        return res;
    }

    // ---------------- Shift ----------------
    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse4.1", "true", "rvv", "true"},
        counts = {IRNode.LSHIFT_VB, ">0"})
    public byte[] vectorShiftLeft() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (a[i] << 3);
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse4.1", "true", "rvv", "true"},
        counts = {IRNode.RSHIFT_VB, ">0"})
    public byte[] vectorSignedShiftRight() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (a[i] >> 2);
        }
        return res;
    }

    @Test
    @IR(applyIfCPUFeatureOr = {"asimd", "true", "sse4.1", "true", "rvv", "true"},
        counts = {IRNode.RSHIFT_VB, ">0"})
    public byte[] vectorUnsignedShiftRight() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = (byte) (a[i] >>> 5);
        }
        return res;
    }
}
