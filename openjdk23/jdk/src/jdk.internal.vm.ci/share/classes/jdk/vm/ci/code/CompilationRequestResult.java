/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package jdk.vm.ci.code;

/**
 * Provides information about the result of a {@link CompilationRequest}.
 */
public interface CompilationRequestResult {

    /**
     * Determines if the compilation was successful.
     *
     * @return a non-null object whose {@link Object#toString()} describes the failure or null if
     *         compilation was successful
     */
    Object getFailure();
}
