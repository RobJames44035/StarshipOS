/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8008627
 * @summary Compiler mishandles three-way return-type-substitutability
 * @compile T8008627.java
 */

class T8008627 {

    interface I {
        Object m(Iterable l);
    }

    interface J<S> {
        S m(Iterable<String> l);
    }

    interface K<T> {
        T m(Iterable<String> l);
    }

    @FunctionalInterface
    interface Functional<S,T> extends I, J<S>, K<T> {}
}
