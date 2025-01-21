/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6509042
 *
 * @summary javac rejects class literals in enum constructors
 * @author Maurizio Cimadamore
 *
 * @compile T6509042.java
 */
enum T6509042 {
     A, B;

     Class<T6509042> cl = T6509042.class;

     T6509042() {
         Class<T6509042> cl2 = T6509042.class;
     }
}
