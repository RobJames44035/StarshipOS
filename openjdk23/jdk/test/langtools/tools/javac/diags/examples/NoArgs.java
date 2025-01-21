/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.misc.no.args
// key: compiler.err.cant.apply.symbol
// key: compiler.misc.arg.length.mismatch
// run: simple

class X {
    void m1(int i) { }

    int x = m1();
}
