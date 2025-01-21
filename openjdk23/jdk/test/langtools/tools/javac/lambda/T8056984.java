/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8056984
 * @summary Ensure that method resolution runs over a captured type variables when checking if
 *          deferred attribution is needed
 * @compile T8056984.java
 */
class T8056984<T1 extends B&C, T2 extends T1> {
    public T8056984(T1 t1, T2 t2) {
        System.err.println(t1.hashCode());
        System.err.println(t2.hashCode());
    }
}
class B {
}
interface C {
    public int hashCode();
}
