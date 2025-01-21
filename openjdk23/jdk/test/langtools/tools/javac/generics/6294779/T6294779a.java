/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug     6294779
 * @summary Problem with interface inheritance and covariant return types
 * @author  Maurizio Cimadamore
 * @compile T6294779a.java
 */

public class T6294779a {

    interface A {
        A m();
    }

    interface B extends A {
        B m();
    }

    interface C extends A {
        C m();
    }

    interface D extends B, C {
        D m();
    }
}
