/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @bug     7169362
 * @author  sogoel
 * @summary Default case for methods other than value() in ContainerAnno
 * @compile DefaultCasePresent.java
 */

import java.lang.annotation.Repeatable;

@Repeatable(FooContainer.class)
@interface Foo {}

@interface FooContainer {
    Foo[] value();
    String other() default "other-method";
}

@Foo @Foo
public class DefaultCasePresent {}

