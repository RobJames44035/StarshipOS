/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.recursive.ctor.invocation

class X {
    X() {
        this();
    }
}
