/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * A marker annotation.  Used so that at least one annotation will be
 * present on the classes tested by MissingTest.
 */
@Retention(RUNTIME)
public @interface Marker {}
