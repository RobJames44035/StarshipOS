/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4916634
 * @summary Wildcard capture
 * @author gafter
 *
 * @compile  -Werror Capture.java
 */

class X<T> {}

class Capture {
    void f(X<? extends Number> x) {
        f2(x);
        f3(x);
    }

    <T> void f2(X<T> x) {}
    <T extends Number> void f3(X<T> x) {}
}
