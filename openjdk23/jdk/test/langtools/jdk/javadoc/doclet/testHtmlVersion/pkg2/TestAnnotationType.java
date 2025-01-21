/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package pkg2;

import java.lang.annotation.*;

/**
 * @deprecated annotation_test1 passes.
 */
@Documented public @interface TestAnnotationType {

    /**
     * @deprecated annotation_test2 passes.
     */
    String optional() default "unknown";

   /**
     * @deprecated annotation_test3 passes.
     */
    int required();
}
