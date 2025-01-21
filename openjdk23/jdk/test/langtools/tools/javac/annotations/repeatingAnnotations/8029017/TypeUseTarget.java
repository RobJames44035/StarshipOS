/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8029017
 * @summary sanity testing of ElementType validation for repeating annotations
 * @compile TypeUseTarget.java
 */

import java.lang.annotation.*;

public class TypeUseTarget {}


// Case 1:
@Target({
    ElementType.TYPE_USE,
})
@Repeatable(Case1Container.class)
@interface Case1 {}

@Target({
    ElementType.ANNOTATION_TYPE,
    ElementType.TYPE,
    ElementType.TYPE_USE,
    ElementType.TYPE_PARAMETER,
})
@interface Case1Container {
  Case1[] value();
}


// Case 2:
@Target({
    ElementType.TYPE_USE,
})
@Repeatable(Case2Container.class)
@interface Case2 {}

@Target({
    ElementType.ANNOTATION_TYPE,
    ElementType.TYPE,
    ElementType.TYPE_USE,
})
@interface Case2Container {
  Case2[] value();
}


// Case 3:
@Target({
    ElementType.TYPE_USE,
})
@Repeatable(Case3Container.class)
@interface Case3 {}

@Target({
    ElementType.ANNOTATION_TYPE,
    ElementType.TYPE,
})
@interface Case3Container {
  Case3[] value();
}


// Case 4:
@Target({
    ElementType.TYPE_USE,
})
@Repeatable(Case4Container.class)
@interface Case4 {}

@Target({
    ElementType.ANNOTATION_TYPE,
})
@interface Case4Container {
  Case4[] value();
}


// Case 5:
@Target({
    ElementType.TYPE_USE,
})
@Repeatable(Case5Container.class)
@interface Case5 {}

@Target({
    ElementType.TYPE,
})
@interface Case5Container {
  Case5[] value();
}


// Case 6:
@Target({
    ElementType.TYPE_USE,
})
@Repeatable(Case6Container.class)
@interface Case6 {}

@Target({
    ElementType.TYPE_PARAMETER,
})
@interface Case6Container {
  Case6[] value();
}
