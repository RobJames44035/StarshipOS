/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8168134
 * @summary Inference: javac incorrectly propagating inner constraint with primitive target
 * @compile T8168134.java
 */

abstract class T8168134 {
    interface W<A> {}
    abstract <B> B f(W<B> e);
    abstract <C> C g(C b, long i);

    void h(W<Long> i) {
        g("", f(i));
    }
}
