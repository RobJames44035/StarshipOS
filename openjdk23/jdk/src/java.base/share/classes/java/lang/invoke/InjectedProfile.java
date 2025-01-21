/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package java.lang.invoke;

import java.lang.annotation.*;

/**
 * Internal marker for some methods in the JSR 292 implementation.
 */
/*non-public*/
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@interface InjectedProfile {
}
