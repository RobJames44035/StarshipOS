/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.annotation.not.valid.for.type

@interface Anno {
    int value();
}

@Anno(@Deprecated)
class AnnoNotValueForType { }
