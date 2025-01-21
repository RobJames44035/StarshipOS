/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.lang.annotation.*;

/**
 * @test
 * @bug 8006547
 * @compile DefaultTarget.java
 */

@Target({
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER,
    ElementType.TYPE,
    ElementType.METHOD,
    ElementType.LOCAL_VARIABLE,
    ElementType.PACKAGE,
    ElementType.ANNOTATION_TYPE,
    ElementType.FIELD,
})
@interface Container {
  DefaultTarget[] value();
}

@Repeatable(Container.class)
public @interface DefaultTarget {}
