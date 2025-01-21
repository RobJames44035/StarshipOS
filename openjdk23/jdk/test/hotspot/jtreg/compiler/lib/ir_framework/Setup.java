/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.lib.ir_framework;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used to identify Setup methods. These can be used to compute arbitrary arguments for a test
 * method (see {@link Test}), as well as to set field values. A test method can use a setup method, by specifying
 * it in a {@link Arguments} annotation. A setup method can optionally take a {@link SetupInfo} as an argument. The
 * arguments for the test methods are returned as a new object array.
 *
 * Examples on how to use test methods can be found in {@link ir_framework.examples.SetupExample} and also as part of the
 * internal testing in the package {@link ir_framework.tests}.
 *
 * @see Arguments
 * @see Setup
 * @see SetupInfo
 * @see Test
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Setup {
}
