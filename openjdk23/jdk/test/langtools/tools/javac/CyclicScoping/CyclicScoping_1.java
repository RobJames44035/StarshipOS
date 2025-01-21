/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4257805
 * @summary Verify detection of cyclically-scoped interface.
 * @author maddox
 *
 * @run compile/fail CyclicScoping_1.java
 */


interface A extends A.B {
    interface B {}
}
