/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @summary Smoke test for repeating annotations
 * @bug 7151010
 *
 * @run clean RepSelfMemberAnno BarContainer BarContainerContainer
 * @run compile RepSelfMemberAnno.java
 */

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Repeatable(BarContainer.class)
public @interface RepSelfMemberAnno {
    @RepSelfMemberAnno @RepSelfMemberAnno
    String meh() default "banan";
}


@Repeatable(BarContainerContainer.class)
@Retention(RetentionPolicy.RUNTIME)
@interface BarContainer {
    RepSelfMemberAnno[] value();
}

@Retention(RetentionPolicy.RUNTIME)
@interface BarContainerContainer {
    BarContainer[] value();
    String meh() default "apa";
}

@BarContainer(value={})
@BarContainer(value={})
@interface Bar {}
