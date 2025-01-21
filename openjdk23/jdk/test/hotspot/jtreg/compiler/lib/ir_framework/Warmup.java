/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation overrides the default number (2000) of times the framework should warm up a test.
 * <ul>
 *     <li><p>Any positive value or zero is permitted. A warm-up of zero allows a simulation of {@code -Xcomp}.</li>
 *     <li><p>Custom run tests (see {@link Run}) must specify a {@code @Warmup} annotation at the run method.</li>
 *     <li><p>Base and checked tests (see {@link Test}, {@link Check}) must specify a {@code @Warmup} annotation at
 *            the test method.</li>
 * </ul>
 *
 * @see Test
 * @see Check
 * @see Run
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Warmup {
    /**
     * The warm-up iterations for the test.
     */
    int value();
}
