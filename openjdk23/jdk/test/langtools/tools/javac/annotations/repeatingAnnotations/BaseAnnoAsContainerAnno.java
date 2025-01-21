/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.annotation.Repeatable;

@Repeatable(Foo.class)
@interface Foo {
    Foo[] value() default {};
}

@Foo @Foo
public class BaseAnnoAsContainerAnno {}

