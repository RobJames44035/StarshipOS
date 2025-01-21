/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.lib.generators;

/**
 * Defines the underlying randomness source used by the generators. This is essentially a subset of
 * {@link java.util.random.RandomGenerator} and the present methods have the same contract.
 * This interface greatly benefits testing, as it is much easier to implement than
 * {@link java.util.random.RandomGenerator}  and thus makes creating test doubles more convenient.
 */
public interface RandomnessSource {
    /** Samples the next long value uniformly at random. */
    long nextLong();
    /** Samples the next long value in the half-open interval [lo, hi) uniformly at random. */
    long nextLong(long lo, long hi);
    /** Samples the next int value uniformly at random. */
    int nextInt();
    /** Samples the next int value in the half-open interval [lo, hi) uniformly at random. */
    int nextInt(int lo, int hi);
    /** Samples the next double value in the half-open interval [lo, hi) uniformly at random. */
    double nextDouble(double lo, double hi);
    /** Samples the next float value in the half-open interval [lo, hi) uniformly at random. */
    float nextFloat(float lo, float hi);
}
