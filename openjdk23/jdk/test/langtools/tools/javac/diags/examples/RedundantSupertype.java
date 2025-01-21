/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.illegal.default.super.call
// key: compiler.misc.redundant.supertype

class RedundantSupertype {
    interface I { default void m() {  } }
    interface J extends I { default void m() {  } }

    static class C implements I, J {
        void foo() { I.super.m(); }
    }
}
