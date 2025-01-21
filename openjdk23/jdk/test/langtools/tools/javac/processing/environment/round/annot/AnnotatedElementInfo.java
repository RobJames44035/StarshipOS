/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

package annot;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Hold information about the set of elements expected to be annotated
 * with a given annotation.
 */
@Retention(RUNTIME)
@Target({TYPE, MODULE, PACKAGE})
public @interface AnnotatedElementInfo {
    String annotationName();
    int expectedSize();
    String[] names();
}
