/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package inheritance.conflict;

class A<T> {
    void f(String s) {}
}

class B<T> extends A<T> {
    void f(T t) {}
}

class C extends B<String> {
}
