/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

// key: compiler.err.name.clash.same.erasure.no.override.1

public class NameClashSameErasureNoOverride1 {

    interface I<X> {
        void m(X l);
    }

    class A {
        void m(Object l) {}
    }

    class B extends A implements I<Integer> {
        public void m(Integer l) {}
    }
}
