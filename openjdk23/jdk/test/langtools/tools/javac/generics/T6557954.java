/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug     6557954
 * @summary Inner class type parameters doesn't get substituted when checking type well-formedness
 * @author Maurizio Cimadamore
 *
 * @compile T6557954.java
 */

class T6557954<T> {
    class Foo<U extends T> {}
    T6557954<Number>.Foo<Integer> f;
}
