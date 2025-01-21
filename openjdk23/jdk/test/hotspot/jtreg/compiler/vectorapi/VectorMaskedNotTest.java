/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.vectorapi;

import compiler.lib.ir_framework.*;

import java.util.Random;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.LongVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

import jdk.test.lib.Asserts;
import jdk.test.lib.Utils;

/**
 * @test
 * @bug 8288397
 * @key randomness
 * @library /test/lib /
 * @requires vm.cpu.features ~= ".*sve.*"
 * @summary AArch64: Fix register issues in SVE backend match rules
 * @modules jdk.incubator.vector
 *
 * @run driver compiler.vectorapi.VectorMaskedNotTest
 */

public class VectorMaskedNotTest {
    private static final VectorSpecies<Integer> I_SPECIES = IntVector.SPECIES_MAX;
    private static final VectorSpecies<Long> L_SPECIES = LongVector.SPECIES_MAX;

    private static int LENGTH = 128;
    private static final Random RD = Utils.getRandomInstance();

    private static int[] ia;
    private static int[] ir;
    private static long[] la;
    private static long[] lr;
    private static boolean[] m;

    static {
        ia = new int[LENGTH];
        ir = new int[LENGTH];
        la = new long[LENGTH];
        lr = new long[LENGTH];
        m = new boolean[LENGTH];

        for (int i = 0; i < LENGTH; i++) {
            ia[i] = RD.nextInt(25);
            la[i] = RD.nextLong(25);
            m[i] = RD.nextBoolean();
        }
    }

    @Test
    @Warmup(10000)
    @IR(counts = { IRNode.VNOT_I_MASKED, ">= 1" })
    public static void testIntNotMasked() {
        VectorMask<Integer> mask = VectorMask.fromArray(I_SPECIES, m, 0);
        IntVector av = IntVector.fromArray(I_SPECIES, ia, 0);
        av.lanewise(VectorOperators.NOT, mask).add(av).intoArray(ir, 0);

        // Verify results
        for (int i = 0; i < I_SPECIES.length(); i++) {
            if (m[i]) {
                Asserts.assertEquals((~ia[i]) + ia[i], ir[i]);
            } else {
                Asserts.assertEquals(ia[i] + ia[i], ir[i]);
            }
        }
    }

    @Test
    @Warmup(10000)
    @IR(counts = { IRNode.VNOT_L_MASKED, ">= 1" })
    public static void testLongNotMasked() {
        VectorMask<Long> mask = VectorMask.fromArray(L_SPECIES, m, 0);
        LongVector av = LongVector.fromArray(L_SPECIES, la, 0);
        av.lanewise(VectorOperators.NOT, mask).add(av).intoArray(lr, 0);

        // Verify results
        for (int i = 0; i < L_SPECIES.length(); i++) {
            if (m[i]) {
                Asserts.assertEquals((~la[i]) + la[i], lr[i]);
            } else {
                Asserts.assertEquals(la[i] + la[i], lr[i]);
            }
        }
    }

    public static void main(String[] args) {
        TestFramework.runWithFlags("--add-modules=jdk.incubator.vector",
                                   "-XX:UseSVE=1");
    }
}
