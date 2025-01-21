/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package pkg;

import java.lang.annotation.Documented;

/**
 * An annotation interface.
 * @since 2.0b
 */
@Documented public @interface TestAnnotation {

    /**
     * Optional annotation interface element.
     * @since 3.2
     */
    String optional() default "unknown";

    /**
     * Required annotation interface element.
     * @since 2.0b
     */
    @Deprecated(forRemoval=true,since="3.2")
    int required();

    /**
     * @since 6
     */
    int field = 0;
}
