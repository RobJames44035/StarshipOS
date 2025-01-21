/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.cyclic.annotation.element

@interface X {
    X value();
}
