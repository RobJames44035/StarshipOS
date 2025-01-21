/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package p;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExpectedToString {
    String value();
}
