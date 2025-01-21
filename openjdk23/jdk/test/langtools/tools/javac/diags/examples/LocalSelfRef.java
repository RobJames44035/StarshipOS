/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

// key: compiler.err.cant.infer.local.var.type
// key: compiler.misc.local.self.ref

class LocalSelfRef {
    void test() {
        var x = m(x);
    }

    String m(String s) { return s; }
}
