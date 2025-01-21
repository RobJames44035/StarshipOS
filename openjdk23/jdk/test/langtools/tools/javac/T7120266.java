/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7120266
 * @summary javac fails to compile hotspot code
 * @compile T7120266.java
 */

class T7120266 {
   void test(int i, int len) { that(i < len, "oopmap"); }
   void that(boolean b, String s) { };
}
