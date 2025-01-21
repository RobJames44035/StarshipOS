/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @summary Basic tests of immutable BitSets
 * @modules java.base/jdk.internal.util
 * @run junit ImmutableBitSet
 */

import jdk.internal.util.ImmutableBitSetPredicate;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.Random;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableBitSet {

    @Test
    void empty() {
        BitSet bs = new BitSet();
        IntPredicate ibs = ImmutableBitSetPredicate.of(bs);
        test(bs, ibs);
    }

    @Test
    void negativeIndex() {
        IntStream.of(0, 127, 128, 129, 143, 4711).forEach(k -> {
                    BitSet bs = new BitSet(k);
                    IntPredicate ibs = ImmutableBitSetPredicate.of(bs);
                    assertFalse(ibs.test(-1));
                    assertFalse(ibs.test(Integer.MIN_VALUE));
                });
    }

    @Test
    void basic() {
        IntStream.of(0, 16, 127, 128, 129, 143, 4711).forEach(k -> basic(k));
    }

    void basic(int length) {
        BitSet bs = createReference(length);
        IntPredicate ibs = ImmutableBitSetPredicate.of(bs);
        test(bs, ibs);
    }

    @Test
    void clearedAtTheTail() {
        IntStream.of(0, 16, 127, 128, 129, 143, 4711).forEach(k -> {
            for (int i = Long.BYTES - 1; i < Long.BYTES + 2; i++) {
                BitSet bs = createReference(k + i);
                for (int j = bs.length() - 1; j > Long.BYTES - 1; j--) {
                    bs.clear(j);
                }
                IntPredicate ibs = ImmutableBitSetPredicate.of(bs);
                test(bs, ibs);
            }
        });
    }

    static void test(BitSet expected, IntPredicate actual) {
        for (int i = 0; i < expected.length() + 17; i++) {
            assertEquals(expected.get(i), actual.test(i), "at index " + i);
        }
    }

    private static BitSet createReference(int length) {
        BitSet result = new BitSet(length);
        Random random = new Random(length);
        for (int i = 0; i < length; i++) {
            result.set(i, random.nextBoolean());
        }
        return result;
    }

}