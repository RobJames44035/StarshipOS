/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary check that well-formed MI hierarchies behaves well w.r.t. method resolution (i.e. no ambiguities)
 * @author  Maurizio Cimadamore
 * @compile Pos06.java
 */

class Pos06 {
     interface A {
         default void m() { Pos06.impl(this); }
     }

     interface B extends A {
         default void m() { Pos06.impl(this); }
     }

     static class X implements A, B { }

     void test(X x) {
         x.m();
         ((A)x).m();
         ((B)x).m();
     }

     static void impl(Object a) { }
}
