/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.unreported.exception.need.to.catch.or.throw

class UnreportedException {
    void m() {
        throw new Exception();
    }
}
