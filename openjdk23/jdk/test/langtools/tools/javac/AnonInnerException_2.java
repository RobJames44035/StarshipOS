/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4266172 4054256
 * @summary Verify that instance initializer of inner class can throw checked exception.
 *
 * (Was Verify that constructor for an anonymous class cannot throw exceptions that its
 * superclass constructor cannot.  This restriction has been lifted -- 4054256.)
 *
 * @author maddox
 *
 * @run compile AnonInnerException_2.java
 */

class AnonInnerException_2 {

    boolean done = true;

    void foo() throws Exception {

        AnonInnerExceptionAux y =
            new AnonInnerExceptionAux() {
              // instance initializer
              {
                  if (done)
                      throw new java.io.IOException();
              }
        };
    }
}

class AnonInnerExceptionAux {
    AnonInnerExceptionAux() throws MyException {}
}

class MyException extends Exception {}
