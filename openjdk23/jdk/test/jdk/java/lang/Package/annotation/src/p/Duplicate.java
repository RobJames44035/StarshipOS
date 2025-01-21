/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package p;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={PACKAGE})
public @interface Duplicate {
}

