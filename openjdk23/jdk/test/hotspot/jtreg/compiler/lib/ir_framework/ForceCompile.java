/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework;

import compiler.lib.ir_framework.shared.TestFormatException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Force a compilation of the annotated <b>helper method</b> (not specifying {@link Test @Test}, {@link Check @Check},
 * or {@link Test @Run}) immediately at the specified level. {@link CompLevel#SKIP} and
 * {@link CompLevel#WAIT_FOR_COMPILATION} do not apply and result in a {@link TestFormatException}.
 *
 * <p>
 * Using this annotation on <i>non-helper</i> methods also results in a {@link TestFormatException}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ForceCompile {
    /**
     * The compilation level to compile the helper method at.
     */
    CompLevel value() default CompLevel.ANY;
}
