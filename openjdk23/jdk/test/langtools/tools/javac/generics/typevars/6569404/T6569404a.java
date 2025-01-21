/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug     6569404
 * @summary Regression: Cannot instantiate an inner class of a type variable
 * @author  mcimadamore
 */

public class T6569404a {

    static class Outer {
      public class Inner {}
    }

    static class Test<T extends Outer> {
       public Test(T t) {
          Outer.Inner inner = t.new Inner();
       }
    }

    public static void main(String[] args) {
       new Test<Outer>(new Outer());
    }
}
