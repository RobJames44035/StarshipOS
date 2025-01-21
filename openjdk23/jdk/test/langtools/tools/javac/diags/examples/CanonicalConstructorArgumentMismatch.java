/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

// key: compiler.err.invalid.canonical.constructor.in.record
// key: compiler.misc.canonical.with.name.mismatch
// key: compiler.misc.canonical

record R(int x) {
    public R(int _x) { this.x = _x; }
}
