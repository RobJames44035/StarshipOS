/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary check that compilation order does not matter
 * @author  Maurizio Cimadamore
 * @compile Pos07.java
 */

class Pos07 {
    interface A {
         default void foo() { Pos07.impl(this); }
         default void bar() { Pos07.impl(this); }
    }

    static class C implements B, A {}

    interface B extends A {
        default void foo() { Pos07.impl(this); }
    }

    static void impl(A a) {}
}
