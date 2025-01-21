/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T6946618a {
    static class C<T> {
      T makeT() {
        return new T(); //error
      }
    }

    static class D<S> {
      C<S> makeC() {
        return new C<S>(); //ok
      }
    }
}
