/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

import java.lang.annotation.*;

/**
 * This is just a test annotation type this is not documented because it
 * is missing the @Documented tag.
 */
public @interface AnnotationTypeUndocumented {

    /**
     * The copyright holder.
     */
    String optional() default "unknown";

   /**
    * The year of the copyright.
    */
    int required();
}
