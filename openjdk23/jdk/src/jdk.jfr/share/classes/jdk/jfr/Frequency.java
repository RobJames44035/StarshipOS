/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Event field annotation, specifies that the value is a frequency, measured in Hz.
 *
 * @since 9
 */
@MetadataDefinition
@ContentType
@Label("Frequency")
@Description("Measure of how often something occurs, in Hertz")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Frequency {
}
