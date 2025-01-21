/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework;

/**
 * This enum is used in {@link Check#when()} of a <b>checked test</b> to specify when the framework will invoke the
 * check method after invoking the associated {@link Test} method.
 *
 * @see Check
 * @see Test
 */
public enum CheckAt {
    /**
     * Default: Invoke the {@link Check} method each time after invoking the associated {@link Test} method.
     */
    EACH_INVOCATION,
    /**
     * Invoke the {@link Check} method only once after the warm-up of the associated {@link Test} method had been completed
     * and the framework has compiled the associated {@link Test} method.
     */
    COMPILED,
}
