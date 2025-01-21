/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

// options: -Xlint:deprecation
// key: compiler.warn.deprecated.annotation.has.no.effect

class DeprecatedAnnotationHasNoEffect {
    void foo(@Deprecated int p) {}
}
