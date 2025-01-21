/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework;

import compiler.lib.ir_framework.shared.TestFormatException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Prevent an inlining of the annotated <b>helper method</b> (not specifying {@link Test @Test}, {@link Check @Check},
 * or {@link Run @Run}). <i>Non-helper methods</i> are never inlined. Explicitly using this annotation on
 * <i>non-helper methods</i> results in a {@link TestFormatException}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DontInline {
}
