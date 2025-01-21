/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4984158
 * @summary two inherited methods with same signature
 * @author gafter, Maurizio Cimadamore
 *
 * @compile  InheritanceConflict2.java
 */

package inheritance.conflict2;

class A<T> {
    void f(String s) {}
}

class B<T> extends A<T> {
    void f(T t) {}
}

class C extends B<String> {
    void f(String s) {}
}
