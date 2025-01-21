/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

// key: compiler.err.prob.found.req
// key: compiler.misc.inconvertible.types
// key: compiler.note.compressed.diags
// key: compiler.note.note
// key: compiler.misc.count.error
// key: compiler.err.error
// run: backdoor

class CompressedDiags {

    void m(String s) { }

    void test() {
        m(1);
    }
}
