/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/*
 * @test
 * @bug 8312560
 * @summary Annotation on Decomposed Record Component in Enhanced For Loop Fails Compilation
 * @compile T8312560.java
 */
public class T8312560 {
    void m(Object o) {
        if (o instanceof R(@A var x)) {}
    }

    @interface A {
    }

    record R(Integer x) {
    }
}
