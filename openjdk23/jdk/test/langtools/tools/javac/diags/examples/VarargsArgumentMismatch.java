/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.cant.apply.symbol
// key: compiler.misc.varargs.argument.mismatch
// key: compiler.misc.inconvertible.types

class VarargsArgumentMismatch {
    void m(String s, Integer... is) {}
    { this.m("1", "2", "3"); }
}
