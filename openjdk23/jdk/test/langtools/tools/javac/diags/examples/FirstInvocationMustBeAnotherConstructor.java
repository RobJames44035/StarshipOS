/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

// key: compiler.err.non.canonical.constructor.invoke.another.constructor

record R(int x) {
    public R(int x, int y) { this.x = x; }
}
