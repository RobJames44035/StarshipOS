/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework;

/**
 * The run mode for a <b>custom run test</b> specified in {@link Run#mode}.
 *
 * @see Run
 */
public enum RunMode {
    /**
     * Default mode: First warm up the run method (if a warm-up is done), then compile the associated {@link Test}
     * method and finally invoke the run method once more.
     */
    NORMAL,
    /**
     * Standalone mode: There is no warm-up and no compilation done by the framework. The run method is responsible to
     * trigger the compilation(s), especially in regard of possible {@link IR} annotations at the associated {@link Test}
     * method.
     */
    STANDALONE,
}
