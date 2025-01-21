/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.cant.apply.symbol
// key: compiler.misc.infer.varargs.argument.mismatch
// key: compiler.misc.inconvertible.types

class InferVarargsArgumentMismatch {
    <X> void m(X x1, String... xs) {}
    { this.m("", 1); }
}
