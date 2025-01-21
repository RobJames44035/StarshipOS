/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.invalid.mref
// key: compiler.misc.static.mref.with.targs

class StaticMrefWithTargs<X> {

    Runnable r = StaticMrefWithTargs<String>::m;

    static void m() { }
}
