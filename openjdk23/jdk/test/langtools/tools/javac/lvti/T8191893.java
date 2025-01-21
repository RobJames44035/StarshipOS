/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8191802 8191893
 * @summary Upward projection result is A<? extends Number> instead of A<? super Integer>
 * @compile T8191893.java
 */

class T8191893 {

    static class A<E> { }

    <T> A<? super T> m(T t) {
        return null;
    }

    <U> A<? super A<? super U>> m2(A<? super U> u) {
        return null;
    }

    void test() {
        var varValue = m2(m(10));
        A<? super A<Object>> expectedTypeValue = varValue;
    }
}
