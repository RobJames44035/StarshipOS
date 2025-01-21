/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.annotation.Repeatable;

@Repeatable(FooContainer.class)
@interface Foo {}

@interface FooContainer {
    Foo[] value();
    String other();  // missing default clause
}

@Foo @Foo
public class MissingDefaultCase1 {}
