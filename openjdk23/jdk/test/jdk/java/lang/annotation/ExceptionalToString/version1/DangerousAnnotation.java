/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface DangerousAnnotation {
    Utopia utopia();
    int thirtyTwoBitsAreNotEnough();
    Class<?> classy();
    Class<?>[] classies();
    Class<?>[] moreClassies();
}
