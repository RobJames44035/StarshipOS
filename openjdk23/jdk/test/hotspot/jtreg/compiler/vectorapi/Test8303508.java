/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package compiler.vectorapi;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.ShortVector;
import jdk.incubator.vector.VectorSpecies;

/*
 * @test
 * @bug 8303508
 * @summary Vector.lane() gets wrong value on x86
 * @modules jdk.incubator.vector
 * @library /test/lib
 *
 * @run main/othervm -Xbatch -XX:-TieredCompilation -ea compiler.vectorapi.Test8303508
 */
public class Test8303508 {

    static final VectorSpecies<Byte> BSPECIES_128 = ByteVector.SPECIES_128;
    static final VectorSpecies<Short> SSPECIES_128 = ShortVector.SPECIES_128;

    static final byte[] ba = {0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15};
    static final short[] sa = {0, -1, -2, -3, -4, -5, -6, -7};

    private static byte vec_extract_byte(int idx) {
        var bv = ByteVector.fromArray(BSPECIES_128, ba, 0);
        return bv.lane(idx);
    }

    private static short vec_extract_short(int idx) {
        var sv = ShortVector.fromArray(SSPECIES_128, sa, 0);
        return sv.lane(idx);
    }

    public static void main(String[] args) {
        int idx = 0;
        int actual = 0;
        int expected = 0;
        for (int i = 0; i < 10000; i++) {
            idx = i & 0xF;
            actual = vec_extract_byte(idx);
            expected = ba[idx];
            if (actual != expected) {
                throw new AssertionError("incorrect result byte extraction, actual = " + actual + " expected = " + expected);
            }
            idx = i & 0x7;
            actual = vec_extract_short(idx);
            expected = sa[idx];
            if (actual != expected) {
                throw new AssertionError("incorrect result short extraction, actual = " + actual + " expected = " + expected);
            }
        }
        System.out.println("PASS");
    }
}
