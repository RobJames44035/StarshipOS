/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8008077
 * @summary Type annotations in a lazy constant need to be attributed
 *   in the correct order.
 * @author Werner Dietl
 * @compile/ref=LazyConstantValue.out LazyConstantValue.java
 */

import java.lang.annotation.*;

class ClassA {
    Object o = ClassB.lcv;
}

class ClassB {
    static final String[] lcv = new @TA String[0];
}

class ClassC {
    static final Object o = (@TA Object) null;
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface TA {}
