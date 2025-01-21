/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Event field annotation, specifies that the value is of an unsigned data type.
 *
 * @since 9
 */
@MetadataDefinition
@ContentType
@Label("Unsigned Value")
@Description("Value should be interpreted as unsigned data type")
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface Unsigned {
}
