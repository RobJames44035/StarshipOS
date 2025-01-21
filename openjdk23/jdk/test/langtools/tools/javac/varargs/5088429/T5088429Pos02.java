/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 5088429
 *
 * @summary varargs overloading problem
 * @author mcimadamore
 * @compile T5088429Pos02.java
 *
 */

class T5088429Pos02 {
    interface A {}
    interface B extends A {}

    void m(A... args) {}
    void m(B b, A... args) {}

    void test(B b) {
        m(b);
    }
}
