/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @summary Smoke test for repeating annotations
 * @bug 7151010
 *
 * @run clean Foos Foo Bars Bar Baz Bazs CheckTargets
 * @run compile CheckTargets.java
 */

import java.lang.annotation.*;

@Repeatable(Foos.class)
@Target(ElementType.TYPE)
@interface Foo {}

@Target(ElementType.ANNOTATION_TYPE)
@interface Foos {
    Foo[] value();
}

@Repeatable(Bars.class)
@Target(ElementType.TYPE)
@interface Bar {}

@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@interface Bars {
    Bar[] value();
}


@Repeatable(Bazs.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@interface Baz {}

@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@interface Bazs {
    Baz[] value();
}


public class CheckTargets {}
