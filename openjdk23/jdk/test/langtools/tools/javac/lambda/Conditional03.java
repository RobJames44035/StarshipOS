/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  conditionals and boxing
 * @compile Conditional03.java
 */

class Conditional03 {

   void m1(Object o) { }
   void m2(int i) { }

   void test(boolean cond) {
       m1((cond ? 1 : 1));
       m1((cond ? box(1) : box(1)));
   }

   Integer box(int i) { return i; }
}
