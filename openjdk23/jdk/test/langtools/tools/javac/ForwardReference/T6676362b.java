/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6676362
 * @summary Spurious forward reference error with final var + instance variable initializer
 * @author Maurizio Cimadamore
 *
 * @compile T6676362b.java
 */

public class T6676362b {
    static final int i1 = T6676362b.i2; //legal - usage is not via simple name
    static final int i2 = i1;
}
