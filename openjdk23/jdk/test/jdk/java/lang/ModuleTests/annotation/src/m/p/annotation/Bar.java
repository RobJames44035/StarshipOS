/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package p.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.MODULE;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={MODULE})
public @interface Bar {
   String value();
}
