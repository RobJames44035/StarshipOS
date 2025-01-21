/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.annotation.missing.default.value

@interface Anno {
    String value();
}

@Anno
class AnnotationMissingValue { }
