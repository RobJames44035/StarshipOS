/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8044546
 * @summary Crash on faulty reduce/lambda
 * @compile NestedInvocationsTest.java
 */

class NestedInvocationsTest<T> {
    boolean foo(I<T> i) {
        return baz(zas(i));
    }

    <U> J<U, Boolean> zas(I<U> i) {
        return null;
    }

    <R> R baz(J<T, R> j) {
        return null;
    }

    interface I<I1> {}

    interface J<J1, J2> {}
}
