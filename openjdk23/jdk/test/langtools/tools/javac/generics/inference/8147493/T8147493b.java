/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8147493
 * @summary regression when type-checking unchecked method calls
 * @compile T8147493b.java
 */

abstract class T8147493b {

    abstract <A> A f(A t);
    abstract <B> Class<B> g(Class<B> x, String y);
    abstract <C> void h(C t);

    void m(Class raw) {
      h(g(raw, f(null)));
    }
}
