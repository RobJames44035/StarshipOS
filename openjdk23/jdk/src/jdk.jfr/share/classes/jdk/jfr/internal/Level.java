/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.jfr.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jdk.jfr.MetadataDefinition;

/**
 * Event annotation, determines filtering level.
 *
 * This settings is only supported for JVM events.
 *
 * @since 22
 */
@MetadataDefinition
@Target({ ElementType.TYPE })
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Level {
    /**
     * Settings name {@code "level"} for configuring filtering level.
     */
    public static final String NAME = "level";

    /**
     * Levels, for example {@code "forRemoval", "all"}.
     *
     * @return the levels, default { "forRemoval", "all" }, not {@code null}
     */
    String[] value() default {};
}
