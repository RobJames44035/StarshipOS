/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.static.declaration.not.allowed.in.inner.classes
// options: --release 15

class EnumsMustBeStatic {
    class Nested {
        enum E { A, B, C }
    }
}
