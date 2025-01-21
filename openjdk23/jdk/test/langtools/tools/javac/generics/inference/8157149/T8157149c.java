/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8157149
 * @summary Inference: weird propagation of thrown inference variables
 *
 * @compile T8157149c.java
 */

class T8157149c  {

    interface I<T extends C<?, ?>, U> {
        T m(U o);
    }

    static class C<T, U> {
        C(T f) { }
    }

    <T, A> void m(I<C<T, Object>, A> o1, A o2) { }

    void test(Object o) {
        m(C::new, o);
    }
}
