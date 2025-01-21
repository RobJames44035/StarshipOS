/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5008452
 * @summary compiler crash if annotation using an enum type defined inside the annotation
 * @author gafter
 *
 * @compile NType.java
 */

package ntype;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@interface NestedEnum1 {
    enum Quality { GOOD, BAD, AVERAGE }
    Quality value();
}

@NestedEnum1(NestedEnum1.Quality.GOOD)
class AnnotationDriverTest {
    public void assertion1() {
    }
    public static void main(String args[]) {
        AnnotationDriverTest ref = new AnnotationDriverTest();
        ref.assertion1();
    }
}
