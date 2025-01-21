/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class InnerNamedConstant_2 {

    static class Inner1 {
        static int x = 1;                  // OK - class is top-level
        static final int y = x * 5;        // OK - class is top-level
        static final String z;             // OK - class is top-level
        static {
            z = "foobar";
        }
    }

    class Inner2 {
        static int x = 1;                  // ERROR - static not final
        static final String z;             // ERROR - static blank final
        {
            z = "foobar";                  // Error may be reported here. See 4278961.
        }
    }

    // This case must go in a separate class, as otherwise the detection
    // of the error is suppressed as a result of recovery from the other
    // errors.

    class Inner3 {
        static final int y = Inner1.x * 5; // ERROR - initializer not constant
    }

}
