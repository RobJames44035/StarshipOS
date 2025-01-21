/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package subpackage;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Container.class)
public @interface Containee {
    int value();
}
