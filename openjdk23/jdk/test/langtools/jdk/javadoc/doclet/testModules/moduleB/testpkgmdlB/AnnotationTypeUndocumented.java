/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package testpkgmdlB;

import java.lang.annotation.*;

/**
 * This is a test annotation type this is not documented because it
 * is missing the @Documented tag.
 */
@Target(ElementType.MODULE) public @interface AnnotationTypeUndocumented {

    /**
     * The copyright holder.
     *
     * @return a string.
     */
    String optional() default "unknown";

   /**
    * The year of the copyright.
    *
    * @return an int.
    */
    int required();
}
