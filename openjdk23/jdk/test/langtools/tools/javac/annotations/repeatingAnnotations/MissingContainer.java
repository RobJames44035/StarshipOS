/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.annotation.Repeatable;

@Repeatable
@interface Foo {}

@interface FooContainer {
    Foo[] value();
}

@Foo @Foo
public class MissingContainer {}
