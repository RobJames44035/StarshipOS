/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.misc.count.error.plural
// key: compiler.err.unreported.exception.need.to.catch.or.throw
// key: compiler.err.error
// run: backdoor

class CountErrorPlural {
    void m1() { throw new Exception(); }
    void m2() { throw new Exception(); }
}
