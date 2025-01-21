/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.lang.annotation.*;

/**
 * @test
 * @bug 8006547 8261088
 * @compile NoTargetOnContainer2.java
 */

@interface FooContainer {
  Foo[] value();
}

@Target({
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER,
    ElementType.TYPE,
    ElementType.METHOD,
    ElementType.LOCAL_VARIABLE,
    ElementType.PACKAGE,
    ElementType.ANNOTATION_TYPE,
    ElementType.FIELD,
    ElementType.TYPE_USE,
    ElementType.TYPE_PARAMETER,
    ElementType.RECORD_COMPONENT,
    ElementType.MODULE,
})
@Repeatable(FooContainer.class)
@interface Foo {}

class NoTargetOnContainer2 {}
