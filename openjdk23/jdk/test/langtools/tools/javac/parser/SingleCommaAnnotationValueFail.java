/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class SingleCommaAnnotationValueFail {
    // Non-example
    @Foo({,0}) void a() { }
}
@interface Foo { int[] value(); }
