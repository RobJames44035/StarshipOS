/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6675483
 *
 * @summary Javac rejects multiple type-variable bound declarations starting with an enum type
 * @author Maurizio Cimadamore
 *
 * @compile T6675483.java
 */

public class T6675483 {
    enum E implements Comparable<E> {}

    interface C<T extends E & Comparable<E>> {
        <S extends E & Comparable<E>> void m();
    }
}
