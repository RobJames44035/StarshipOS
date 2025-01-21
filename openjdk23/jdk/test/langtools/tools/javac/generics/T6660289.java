/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug     6660289
 * @summary declared bound in inner class referring a type variable of the outer class
 * @author Maurizio Cimadamore
 * @compile T6660289.java
 */

public class T6660289<E> {
     class Inner<S extends E> {}
}
