/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Event field annotation, specifies that the event will soon transition to a thread.
 *
 * @since 9
 */
@MetadataDefinition
@Label("Transition To")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TransitionTo {
}
