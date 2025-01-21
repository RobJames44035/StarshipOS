/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jdeprusage;

import jdk.deprcases.types.DeprecatedAnnotation;

public class UseAnnotation {
    @DeprecatedAnnotation
    static class AnnotatedClass { }

    static class AnnotatedMethod {
        @DeprecatedAnnotation
        void foo() { }
    }

    static class AnnotatedField {
        @DeprecatedAnnotation
        int foo = 1;
    }
}
