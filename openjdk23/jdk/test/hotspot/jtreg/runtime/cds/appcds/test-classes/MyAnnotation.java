/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface MyAnnotation {
    public String name();
    public String value();
}
