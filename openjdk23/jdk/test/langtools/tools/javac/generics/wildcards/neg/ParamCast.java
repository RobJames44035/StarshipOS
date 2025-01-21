/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class A<T> {}
class B<S, T> extends A<T> {}

class Main {
    void f(A<String> as) {
        Object o = (B<?, Integer>) as;
    }
}
