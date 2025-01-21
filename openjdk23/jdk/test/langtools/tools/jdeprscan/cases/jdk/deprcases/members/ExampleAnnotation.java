/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.deprcases.members;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

@Retention(value=RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD, FIELD})
public @interface ExampleAnnotation {
    String file();
    @Deprecated String name() default "";
}
