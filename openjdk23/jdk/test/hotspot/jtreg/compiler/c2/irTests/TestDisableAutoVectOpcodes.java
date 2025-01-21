/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8275275
 * @summary Fix performance regression after auto-vectorization on aarch64 NEON.
 * @requires os.arch=="aarch64"
 * @library /test/lib /
 * @run driver compiler.c2.irTests.TestDisableAutoVectOpcodes
 */

public class TestDisableAutoVectOpcodes {

    final private static int SIZE = 3000;

    private static double[] doublea = new double[SIZE];
    private static double[] doubleb = new double[SIZE];
    private static long[] longa = new long[SIZE];
    private static long[] longb = new long[SIZE];
    private static int[] inta = new int[SIZE];
    private static float[] floata = new float[SIZE];
    private static float[] floatb = new float[SIZE];
    private static float fresult;
    private static double dresult;

    public static void main(String[] args) {
        TestFramework.runWithFlags("-XX:UseSVE=0");
    }

    @Test
    @IR(failOn = {IRNode.VECTOR_CAST_D2I})
    private static void testConvD2I() {
        for(int i = 0; i < SIZE; i++) {
            inta[i] = (int) (doublea[i]);
        }
    }

    @Test
    @IR(failOn = {IRNode.VECTOR_CAST_L2F})
    private static void testConvL2F() {
        for(int i = 0; i < SIZE; i++) {
            floata[i] = (float) (longa[i]);
        }
    }

    @Test
    @IR(failOn = {IRNode.MUL_VL})
    private static void testMulVL() {
        for(int i = 0; i < SIZE; i++) {
            longa[i] = longa[i] * longb[i];
        }
    }

    @Test
    @IR(failOn = {IRNode.ADD_REDUCTION_VF})
    private static void testAddReductionVF() {
        float result = 1;
        for(int i = 0; i < SIZE; i++) {
            result += (floata[i] + floatb[i]);
        }
        fresult += result;
    }

    @Test
    @IR(failOn = {IRNode.ADD_REDUCTION_VD})
    private static void testAddReductionVD() {
        double result = 1;
        for(int i = 0; i < SIZE; i++) {
            result += (doublea[i] + doubleb[i]);
        }
        dresult += result;
    }

    @Test
    @IR(failOn = {IRNode.MUL_REDUCTION_VF})
    private static void testMulReductionVF() {
        float result = 1;
        for(int i = 0; i < SIZE; i++) {
            result *= (floata[i] + floatb[i]);
        }
        fresult += result;
    }

    @Test
    @IR(failOn = {IRNode.MUL_REDUCTION_VD})
    private static void testMulReductionVD() {
        double result = 1;
        for(int i = 0; i < SIZE; i++) {
            result *= (doublea[i] + doubleb[i]);
        }
        dresult += result;
    }

    @Test
    @IR(failOn = {IRNode.COUNT_TRAILING_ZEROS_VL})
    public void testNumberOfTrailingZeros() {
        for (int i = 0; i < SIZE; ++i) {
            inta[i] = Long.numberOfTrailingZeros(longa[i]);
        }
    }

    @Test
    @IR(failOn = {IRNode.COUNT_LEADING_ZEROS_VL})
    public void testNumberOfLeadingZeros() {
        for (int i = 0; i < SIZE; ++i) {
            inta[i] = Long.numberOfLeadingZeros(longa[i]);
        }
    }

}
