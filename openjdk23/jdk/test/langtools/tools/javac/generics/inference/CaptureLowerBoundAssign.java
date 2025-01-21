/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8075793
 * @summary Capture variable as an inference lower bound followed by an invariant assignment
 * @compile CaptureLowerBoundAssign.java
 */

class CaptureLowerBoundAssign {

    static class C<T> {}

    <T> C<T> m(C<? extends T> x) { return null; }

    void test(C<? extends Number> arg) {
        C<Number> c = m(arg);
    }

}