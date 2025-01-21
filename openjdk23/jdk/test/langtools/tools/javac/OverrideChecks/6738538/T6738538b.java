/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6738538 6687444
 * @summary  javac crashes when using a type parameter as a covariant method return type
 * @author Maurizio Cimadamore
 *
 * @compile T6738538b.java
 */

class T6738538b {
    interface I1 {
        Object m();
    }

    interface I2 {}

    class C1<T> implements I1 {
        public T m() {
            return null;
        }
    }

    class C2<T extends C1<?> & I2> {}
}
