/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.types.incompatible
// key: compiler.misc.incompatible.abstract.default

class TypesIncompatibleAbstractDefault {
    interface A {
        default void m() { }
    }

    interface B {
        void m();
    }

    interface AB extends A, B { }
}
