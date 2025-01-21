/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4266172
 * @summary Verify that fix for 4266172 does not affect named inner classes.
 * @author maddox
 *
 * @run compile/fail AnonInnerException_3.java
 */

class AnonInnerException_3 {

    void foo() throws Exception {

        class Inner extends AnonInnerExceptionAux {};

        AnonInnerExceptionAux x = new Inner();

    }
}

class AnonInnerExceptionAux {
    AnonInnerExceptionAux() throws Exception {}
}
