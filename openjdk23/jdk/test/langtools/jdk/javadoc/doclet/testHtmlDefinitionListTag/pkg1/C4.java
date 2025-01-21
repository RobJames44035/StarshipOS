/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package pkg1;

import java.lang.annotation.*;

/*
 * The @Inherited annotation has no effect when applied to an interface.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface C4 {
    boolean value() default true;
}
