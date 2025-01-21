/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package subpackage;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface NonRepeated {
    int value() default 10;
}
