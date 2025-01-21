/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.illegal.default.super.call
// key: compiler.misc.overridden.default

class OverriddenDefault {
    interface I { default void m() {  } }
    interface J extends I { default void m() {  } }
    interface K extends I {}

    static class C implements J, K {
        void foo() { K.super.m(); }
    }
}
