/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package jdk.vm.ci.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a field that should have the default value for its type in an ahead of time image.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface NativeImageReinitialize {
}
