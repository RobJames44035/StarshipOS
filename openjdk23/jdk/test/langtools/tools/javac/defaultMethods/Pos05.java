/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary check that indirectly inherited default methods are discovered during resolution
 * @author  Maurizio Cimadamore
 * @compile Pos05.java
 */

class Pos05  {
     interface A {
         default void m() { Pos05.impl(this); }
     }

     interface B extends A { }

     static class E implements B { }

     void test(E e) {
         e.m();
     }

     static void impl(A a) { }
}
