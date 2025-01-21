/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.lib.ir_framework;

/**
 * Info optionally passed to {@link Setup} annotated methods.
 *
 * @see Setup
 */
public record SetupInfo(int invocationCounter) {

    /**
     * Get the invocation counter, which increments with every invocation of the setup method. It allows the creation
     * of deterministically different inputs to the test method for every invocation.
     */
    @Override
    public int invocationCounter() {
        return invocationCounter;
    }
}
