/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

// key: compiler.err.invalid.accessor.method.in.record
// key: compiler.misc.accessor.method.must.not.be.static

record R(int x) {
    static final int j = 0;
    static public int x() { return j; }
}
