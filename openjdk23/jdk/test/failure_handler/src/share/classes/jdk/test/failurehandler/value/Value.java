/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.failurehandler.value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface Value {
    String name();
    Class<? extends ValueParser> parser() default DefaultParser.class;
}
