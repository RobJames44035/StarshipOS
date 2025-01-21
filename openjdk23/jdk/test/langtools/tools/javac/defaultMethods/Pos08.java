/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary check that common overrider solves default method conflicts
 * @author  Maurizio Cimadamore
 * @compile Pos08.java
 */

class Pos08 {
    interface A {
        default void m() { Pos08.a(this); }
    }

    interface B {
        default void m() { Pos08.b(this); }
    }

    interface C extends A, B {
        default void m() { Pos08.b(this); }
    }

    static void a(A o) { }
    static void b(B o) { }
}
