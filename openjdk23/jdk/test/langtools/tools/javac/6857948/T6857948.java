/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class Foo {
   Foo(String v) {}
};

class Test {
   public static void meth() {
      Foo f = new Foo("Hello!",nosuchfunction()) {};
   }
}
