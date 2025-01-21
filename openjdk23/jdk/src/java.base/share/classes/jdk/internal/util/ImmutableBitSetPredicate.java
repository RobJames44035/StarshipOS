/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.internal.util;

import jdk.internal.ValueBased;
import jdk.internal.vm.annotation.Stable;

import java.util.BitSet;
import java.util.function.IntPredicate;

/**
 * Class for working with immutable BitSets.
 */
@ValueBased
public class ImmutableBitSetPredicate implements IntPredicate {

    @Stable
    private final long[] words;

    private ImmutableBitSetPredicate(BitSet original) {
        // If this class is made public, we need to do
        // a defensive array copy as certain BitSet implementations
        // may return a shared array. The spec says the array should be _new_ though but
        // the consequences might be unspecified for a malicious BitSet.
        this.words = original.toLongArray();
    }

    /**
     * @param bitIndex the bit index to test
     * @return true if the bit is in the range of the BitSet and the bit is set, otherwise false
     */
    @Override
    public boolean test(int bitIndex) {
        if (bitIndex < 0) {
            return false;
        }

        int wordIndex = bitIndex >> 6;
        return (wordIndex < words.length)
                && ((words[wordIndex] & (1L << bitIndex)) != 0);
    }

    /**
     * {@return a new {@link IntPredicate } representing the {@link BitSet#get(int)} method applied
     * on an immutable snapshot of the current state of this BitSet}.
     * <p>
     * If the returned predicate is invoked with a {@code bitIndex} that is negative, the predicate
     * will throw an IndexOutOfBoundsException just as the {@link BitSet#get(int)} method would.
     * <p>
     * Returned predicates are threadsafe and can be used without external synchronisation.
     *
     * @implNote The method is free to return a {@link ValueBased} implementation.
     *
     * @since 22
     */
    public static IntPredicate of(BitSet original) {
        if (original.size() <= 128) {
            long[] array = original.toLongArray();
            return new SmallImmutableBitSetPredicate(
                    array.length > 0 ? array[0] : 0L,
                    array.length > 1 ? array[1] : 0L);
        }
        return new ImmutableBitSetPredicate(original);
    }

    /**
     * Specialization for small sets of 128 bits or less
     * @param first - bits index 0 through 63, inclusive
     * @param second - bits index 64 through 127, inclusive
     */
    public record SmallImmutableBitSetPredicate(long first, long second) implements IntPredicate {

        /**
         * @param bitIndex the bit index to test
         * @return true if the bit is in the range of the BitSet and the bit is set, otherwise false
         */
        @Override
        public boolean test(int bitIndex) {
            if (bitIndex < 0) {
                return false;
            }

            int wordIndex = bitIndex >> 6;
            if (wordIndex > 1) {
                return false;
            }
            long bits = wordIndex == 0 ? first : second;
            return (bits & (1L << bitIndex)) != 0;
        }
    }
}
