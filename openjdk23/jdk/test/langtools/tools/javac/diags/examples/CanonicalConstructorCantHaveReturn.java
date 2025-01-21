/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

// key: compiler.err.invalid.canonical.constructor.in.record
// key: compiler.misc.canonical.cant.have.return.statement
// key: compiler.misc.compact

record R() {
    public R {
        return;
    }
}

