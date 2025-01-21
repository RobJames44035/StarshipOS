/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.types.incompatible
// key: compiler.misc.incompatible.unrelated.defaults

class TypesIncompatibleUnrelatedDefaults {
    interface A {
        default void m() { }
    }

    interface B {
        default void m() { }
    }

    interface AB extends A, B { }
}
