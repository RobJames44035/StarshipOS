/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class Neg07 {
   static class SuperFoo<X> {}
   static class Foo<X extends Number> extends SuperFoo<X> {
       Foo(X x) {}
   }

   SuperFoo<String> sf1 = new Foo<>("");
   SuperFoo<String> sf2 = new Foo<>("") {};
}
