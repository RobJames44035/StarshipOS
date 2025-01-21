/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

// key: compiler.err.invalid.accessor.method.in.record
// key: compiler.misc.accessor.method.must.not.be.generic

record R(int i) {
    public <T> int i() {
        return i;
    }
}
