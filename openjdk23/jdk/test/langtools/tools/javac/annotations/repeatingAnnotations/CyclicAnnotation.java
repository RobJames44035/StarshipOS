/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.annotation.Repeatable;

@Repeatable(Foo.class)
@interface Baz {
    Foo[] value() default {};
}

@Repeatable(Baz.class)
@interface Foo{
    Baz[] value() default {};
}

@Foo(value = {@Baz,@Baz})
@Baz(value = {@Foo,@Foo})
public class CyclicAnnotation {}
