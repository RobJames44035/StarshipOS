/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.vectorapi;

import jdk.incubator.vector.LongVector;

/*
 * @test
 * @bug 8275643
 * @summary Test that LongVector.neg is properly handled by the _VectorUnaryOp C2 intrinsic
 * @modules jdk.incubator.vector
 * @requires vm.debug
 * @run main/othervm -XX:-TieredCompilation -XX:+AlwaysIncrementalInline -Xbatch
 *                   -XX:CompileCommand=dontinline,compiler.vectorapi.TestLongVectorNeg::test
 *                   compiler.vectorapi.TestLongVectorNeg
 */
public class TestLongVectorNeg {

    static LongVector test(LongVector v) {
        return v.neg();
    }

    public static void main(String[] args) {
        LongVector v = LongVector.zero(LongVector.SPECIES_128);
        for (int i = 0; i < 50_000; ++i) {
            v.abs(); // Warmup to make sure the !VO_SPECIAL code path is taken as well
            test(v);
        }
    }
}
