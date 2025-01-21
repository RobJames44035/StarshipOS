/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

// key: compiler.err.anonymous.diamond.method.does.not.override.superclass
// key: compiler.misc.diamond.anonymous.methods.implicitly.override

class X {
    interface  Foo<T> {
        void g(T t);
    }
    void m() {
      Foo<String> fs = new Foo<>() {
          public void g(String s) { }
          void someMethod() { }
      };
   }
}
