/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6938454
 *
 * @summary Unable to determine generic type in program that compiles under Java 6
 * @author mcimadamore
 * @compile T6938454a.java
 *
 */

class T6938454a {

    static abstract class A { }

    static class B extends A { }

    B getB(B b) {
        return makeA(b);
    }

    <X extends A, Y extends X> Y makeA(X x) {
        return (Y)new B();
    }
}
