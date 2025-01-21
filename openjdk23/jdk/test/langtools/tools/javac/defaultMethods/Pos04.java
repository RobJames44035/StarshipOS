/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary test for overriding with default method
 * @author  Maurizio Cimadamore
 * @compile Pos04.java
 */

class Pos04 {
    interface A { default int m() { return Pos04.m(this); } }
    static abstract class B { public int m() { return 0; } }

    static class C extends B implements A {
        void test() {
            C.this.m();
            A.super.m();
        }
    }

    static int m(A a) { return 0; }
}
