/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(ExpectedClasses.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpectedClass {
    String className();
    String[] expectedMethods() default {};
    String[] expectedFields() default {};
    int expectedNumberOfSyntheticMethods() default 0;
    int expectedNumberOfSyntheticFields() default 0;
}
