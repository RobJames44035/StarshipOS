/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

// key: compiler.err.name.clash.same.erasure.no.override

public class NameClashSameErasureNoOverride<X> {
    static class A {
        void m(NameClashSameErasureNoOverride<String> l) {}
    }

    static class B extends A {
        void m(NameClashSameErasureNoOverride<Integer> l) {}
    }
}
