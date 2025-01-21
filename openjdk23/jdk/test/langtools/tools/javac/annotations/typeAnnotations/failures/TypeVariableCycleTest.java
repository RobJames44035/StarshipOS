/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8023768
 * @summary Type annotations on a type variable, where the bound of
 *   the type variable is an annotated type variable,
 *   need to be processed correctly.
 * @author Werner Dietl
 * @compile TypeVariableCycleTest.java
 */

import java.lang.annotation.*;

class TypeVariableCycleTest<CTV> {
    <MTV extends  @TA CTV> MTV cast(CTV p) {
        return (@TB MTV) p;
    }
}

@Target(ElementType.TYPE_USE)
@interface TA {}

@Target(ElementType.TYPE_USE)
@interface TB {}
