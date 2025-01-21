/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package b;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD, FIELD})
public @interface B {
}
