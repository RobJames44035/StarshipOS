/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package annot;

import java.lang.annotation.*;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

/**
 * A marker annotation.
 */
@Retention(RUNTIME)
@Target({TYPE, MODULE, PACKAGE})
public @interface MarkerAnnot {
}
