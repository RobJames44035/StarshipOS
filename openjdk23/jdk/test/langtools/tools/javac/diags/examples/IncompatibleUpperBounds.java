/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

//key: compiler.err.cant.apply.symbols
//key: compiler.misc.inapplicable.method
//key: compiler.misc.arg.length.mismatch
//key: compiler.misc.incompatible.upper.bounds

import java.util.List;

class IncompatibleUpperBounds {
    <S> void m(List<? super S> s1, List<? super S> s2) { }
    void m(Object o) {}

    void test(List<Integer> li, List<String> ls) {
        m(li, ls);
    }
}
