/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 7034511 7040883 7041019
 * @summary Bogus type-variable substitution with array types with dependencies on accessibility check
 *
 * @compile T7041019.java
 */
import java.util.List;

class T7041019 {
   <E> List<E>[] m(List<E> l) { return null; }

   void test(List<? extends String> ls) {
      int i = m(ls).length;
   }
}
