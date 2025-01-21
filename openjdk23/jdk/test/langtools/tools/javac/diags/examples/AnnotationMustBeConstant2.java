/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

// key: compiler.err.attribute.value.must.be.constant

@T(a = AnnotationMustBeConstant2.x)
@interface T {
    int a();
}

class AnnotationMustBeConstant2 {
    static int x;
}
