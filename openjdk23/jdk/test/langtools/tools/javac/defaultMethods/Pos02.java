/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary test for explicit resolution of ambiguous default methods
 * @author  Maurizio Cimadamore
 * @compile Pos02.java
 */

class Pos02 {
    interface IA { default int m() { return Pos02.m1(this); } }
    interface IB { default int m() { return Pos02.m2(this); } }

    static class A implements IA {}
    static class B implements IB {}

    static class AB implements IA, IB {
        public int m() { return 0; }
        void test() {
            AB.this.m();
            IA.super.m();
        }
    }

    static int m1(IA a) { return 0; }
    static int m2(IB b) { return 0; }
}
