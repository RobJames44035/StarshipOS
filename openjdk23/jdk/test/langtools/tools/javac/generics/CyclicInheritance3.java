/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4861743
 * @summary generics: incorrect cyclic inheritance error with type parameters
 *
 * @compile  CyclicInheritance3.java
 */

class Cycle {
    class A<T> {}
    class B extends A<C> {}
    class C extends B {}
}
