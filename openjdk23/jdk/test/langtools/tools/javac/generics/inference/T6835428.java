/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6835428
 * @author mcimadamore
 * @summary regression: return-type inference rejects valid code
 * @compile T6835428.java
 */

class T6835428<T> {
    interface X<T> {}
   <T extends Comparable<? super T>> T6835428<X<T>> m() { return null; }
   <T extends Comparable<? super T>> void test() {
      T6835428<X<T>> t = m();
   }
}
