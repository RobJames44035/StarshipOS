/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to allow to specify multiple {@link IR @IR} annotations at a {@link Test @Test} method.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IRs {
    IR[] value();
}
