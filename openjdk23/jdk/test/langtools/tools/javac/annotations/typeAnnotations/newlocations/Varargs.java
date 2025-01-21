/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @summary test acceptance of varargs annotations
 * @author Mahmood Ali
 * @compile Varargs.java
 */

import java.lang.annotation.*;

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A {}

class Varargs {

    // Handle annotations on a varargs element type
    void varargPlain(Object @A... objs) {

    }

    void varargGeneric(Class<?> @A ... clz) {
    }
}
