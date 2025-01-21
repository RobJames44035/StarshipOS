/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package annot;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Name {
    String value();
}
