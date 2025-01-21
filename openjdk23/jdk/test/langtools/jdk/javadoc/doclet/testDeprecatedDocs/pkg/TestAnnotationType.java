/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

import java.lang.annotation.*;

/**
 * @deprecated annotation_test1 passes.
 */
@Deprecated(forRemoval=true)
@Documented public @interface TestAnnotationType {

    /**
     * @deprecated annotation_test2 passes.
     */
    String optional() default "unknown";

   /**
     * @deprecated annotation_test3 passes.
     */
    @Deprecated(forRemoval=true)
    int required();

    /**
     * @deprecated annotation_test4 passes.
     */
    @Deprecated(forRemoval=true)
    int field = 0;
}
