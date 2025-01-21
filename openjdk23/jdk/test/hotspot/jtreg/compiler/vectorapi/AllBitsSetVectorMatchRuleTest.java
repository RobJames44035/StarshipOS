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
 * @bug 8287984
 * @key randomness
 * @library /test/lib /
 * @requires vm.compiler2.enabled
 * @requires vm.cpu.features ~= ".*asimd.*"
 * @summary AArch64: [vector] Make all bits set vector sharable for match rules
 * @modules jdk.incubator.vector
 *
 * @run driver compiler.vectorapi.AllBitsSetVectorMatchRuleTest
 */

public class AllBitsSetVectorMatchRuleTest {
    private static final VectorSpecies<Integer> I_SPECIES = IntVector.SPECIES_MAX;
    private static final VectorSpecies<Long> L_SPECIES = LongVector.SPECIES_MAX;

    private static int LENGTH = 128;
    private static final Random RD = Utils.getRandomInstance();

    private static int[] ia;
    private static int[] ib;
    private static int[] ir;
    private static boolean[] ma;
    private static boolean[] mb;
    private static boolean[] mc;
    private static boolean[] mr;

    static {
        ia = new int[LENGTH];
        ib = new int[LENGTH];
        ir = new int[LENGTH];
        ma = new boolean[LENGTH];
        mb = new boolean[LENGTH];
        mc = new boolean[LENGTH];
        mr = new boolean[LENGTH];

        for (int i = 0; i < LENGTH; i++) {
            ia[i] = RD.nextInt(25);
            ib[i] = RD.nextInt(25);
            ma[i] = RD.nextBoolean();
            mb[i] = RD.nextBoolean();
            mc[i] = RD.nextBoolean();
        }
    }

    @Test
    @Warmup(10000)
    @IR(counts = { IRNode.VAND_NOT_I, " >= 1" })
    public static void testAllBitsSetVector() {
        IntVector av = IntVector.fromArray(I_SPECIES, ia, 0);
        IntVector bv = IntVector.fromArray(I_SPECIES, ib, 0);
        av.not().lanewise(VectorOperators.AND_NOT, bv).intoArray(ir, 0);

        // Verify results
        for (int i = 0; i < I_SPECIES.length(); i++) {
            Asserts.assertEquals((~ia[i]) & (~ib[i]), ir[i]);
        }
    }

    @Test
    @Warmup(10000)
    @IR(counts = { IRNode.VAND_NOT_L, " >= 1" }, applyIf = {"UseSVE", "0"})
    @IR(counts = { IRNode.VMASK_AND_NOT_L, " >= 1" }, applyIf = {"UseSVE", "> 0"})
    public static void testAllBitsSetMask() {
        VectorMask<Long> avm = VectorMask.fromArray(L_SPECIES, ma, 0);
        VectorMask<Long> bvm = VectorMask.fromArray(L_SPECIES, mb, 0);
        VectorMask<Long> cvm = VectorMask.fromArray(L_SPECIES, mc, 0);
        avm.andNot(bvm).andNot(cvm).intoArray(mr, 0);

        // Verify results
        for (int i = 0; i < L_SPECIES.length(); i++) {
            Asserts.assertEquals((ma[i] & (!mb[i])) & (!mc[i]), mr[i]);
        }
    }

    public static void main(String[] args) {
        TestFramework.runWithFlags("--add-modules=jdk.incubator.vector");
    }
}
