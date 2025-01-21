/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @summary Smoke test for repeating annotations
 * @bug 7151010
 *
 * @run clean Foos Foo
 * @run compile/fail InvalidTarget.java
 */

import java.lang.annotation.*;

@Repeatable(Foos.class)
@Target(ElementType.ANNOTATION_TYPE)
@interface Foo {}

@Target(ElementType.TYPE)
@interface Foos {
    Foo[] value();
}

public class InvalidTargets {}
