/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4695415
 * @summary generics: bug in type inference when method result used as an argument
 * @author gafter
 *
 * @compile  T4695415.java
 */

class X<T extends Number> {
    static <T extends Number> X<T> f(X<T> a, X<T> b) {
        return f(f(a, b), b);
    }
}
