/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Annotation type with a default value whose class will be missing
 * when MissingTest is run.
 */
@Retention(RUNTIME)
public @interface MissingDefault {
    Class<?> value() default Missing.class;
}
