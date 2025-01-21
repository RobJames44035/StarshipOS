/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug     7148242
 * @summary Regression: valid code rejected during generic type well-formedness check
 * @compile T7148242.java
 */
class T7148242 {
   static abstract class A<K, V, I extends Pair<K, V>, I2 extends Pair<V, K>> {
      abstract A<V, K, I2, I> test();
   }
   static class Pair<K, V> { }
}
