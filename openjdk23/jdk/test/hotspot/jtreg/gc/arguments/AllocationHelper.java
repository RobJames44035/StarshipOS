/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package gc.arguments;

import java.util.concurrent.Callable;

/**
 * Helper class which allocates memory.
 *
 * Typical usage:
 * <pre>
 * {@code
 *           AllocationHelper allocator = new AllocationHelper(MAX_ITERATIONS, ARRAY_LENGTH, CHUNK_SIZE,
 *                   () -> (verifier()));
 *           // Allocate byte[CHUNK_SIZE] ARRAY_LENGTH times. Total allocated bytes will be CHUNK_SIZE * ARRAY_LENGTH + refs length.
 *           // Then invoke verifier and iterate MAX_ITERATIONS times.
 *           allocator.allocateMemoryAndVerify();
 * }
 * </pre>
 */
public final class AllocationHelper {

    private final int arrayLength;
    private final int maxIterations;
    private final int chunkSize;

    // garbageStorage is used to store link to garbage to prevent optimization.
    private static Object garbageStorage;
    private byte garbage[][];
    private final Callable<?> verifierInstance;

    /**
     * Create an AllocationHelper with specified iteration count, array length, chunk size and verifier.
     *
     * @param maxIterations
     * @param arrayLength
     * @param chunkSize
     * @param verifier - Callable instance which will be invoked after all allocation cycle. Can be null;
     */
    public AllocationHelper(int maxIterations, int arrayLength, int chunkSize, Callable<?> verifier) {
        if ((arrayLength <= 0) || (maxIterations <= 0) || (chunkSize <= 0)) {
            throw new IllegalArgumentException("maxIterations, arrayLength and chunkSize should be greater then 0.");
        }
        this.arrayLength = arrayLength;
        this.maxIterations = maxIterations;
        this.chunkSize = chunkSize;
        verifierInstance = verifier;
        garbage = new byte[this.arrayLength][];
        garbageStorage = garbage;
    }

    private void allocateMemoryOneIteration() {
        for (int j = 0; j < arrayLength; j++) {
            garbage[j] = new byte[chunkSize];
        }
    }

    /**
     * Allocate memory and invoke Verifier during all iteration.
     *
     * @throws java.lang.Exception
     */
    public void allocateMemoryAndVerify() throws Exception {
        for (int i = 0; i < maxIterations; i++) {
            allocateMemoryOneIteration();
            if (verifierInstance != null) {
                verifierInstance.call();
            }
        }
    }

    /**
     * The same as allocateMemoryAndVerify() but hides OOME
     *
     * @throws Exception
     */
    public void allocateMemoryAndVerifyNoOOME() throws Exception {
        try {
            allocateMemoryAndVerify();
        } catch (OutOfMemoryError e) {
            // exit on OOME
        }
    }

    /**
     * Release link to allocated garbage to make it available for further GC
     */
    public void release() {
        if (garbage != null) {
            garbage = null;
            garbageStorage = null;
        }
    }
}
