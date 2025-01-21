/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Annotation wrapper around an annotation whose class will be missing
 * when MissingTest is run.
 */
@Retention(RUNTIME)
public @interface MissingWrapper {
    Missing value();
}
