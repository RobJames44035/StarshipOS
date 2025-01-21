/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that syntax for selecting generic receiver works
 * @author  Maurizio Cimadamore
 * @compile MethodReference07.java
 */

class MethodReference07 {
    interface SAM {
       String m(Foo<String> f);
    }

    static class Foo<X> {
       String getX() { return null; }

       static void test() {
          SAM s = Foo<String>::getX;
       }
    }
}
