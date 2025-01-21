/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8261088
 * @summary Repeatable annotations without Target cannot have containers that target module declarations
 * @compile T8261088.java
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

@Target(ElementType.MODULE)
@interface TC {
    T[] value() default {};
}

@Repeatable(TC.class)
@interface T {}
