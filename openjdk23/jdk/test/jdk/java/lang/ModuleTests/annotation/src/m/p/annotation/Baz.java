/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package p.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.MODULE;

@Target(value={MODULE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Baz {
   String[] value();
}
