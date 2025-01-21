/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.misc.count.error
// key: compiler.err.unreported.exception.need.to.catch.or.throw
// key: compiler.err.error
// run: backdoor

class CountError {
    void m() { throw new Exception(); }
}
