/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6943289 7020044
 *
 * @summary Project Coin: Improved Exception Handling for Java (aka 'multicatch')
 * @author mcimadamore
 * @compile Pos09.java
 *
 */

class Pos09 {

    static class Foo<X> {
       Foo(X x) {}
    }

    static interface Base<X> {}
    static class A extends Exception implements Base<String> {}
    static class B extends Exception implements Base<Integer> {}

    void m() {
        try {
            if (true) {
                throw new A();
            }
            else {
                throw new B();
            }
        } catch (A | B ex) {
            Foo<?> f = new Foo<>(ex);
        }
    }
}
