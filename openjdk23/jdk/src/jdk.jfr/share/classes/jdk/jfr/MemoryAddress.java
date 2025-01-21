/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Event field annotation, specifies that the value is a memory address.
 *
 * @since 9
 */
@MetadataDefinition
@ContentType
@Label("Memory Address")
@Description("Represents a physical memory address")
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
public @interface MemoryAddress {
}
