/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

// key: compiler.err.sealed.or.non.sealed.local.classes.not.allowed

class Outer {
    void m() {
        sealed class S { }
    }
}
