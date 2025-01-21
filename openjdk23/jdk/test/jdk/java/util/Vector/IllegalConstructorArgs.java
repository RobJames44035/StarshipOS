/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4061897
 * @summary Test for illegal argument exception
 */

import java.util.Vector;

/**
 * This is a simple test class created to check for
 * an exception when a new Vector is constructed with
 * illegal arguments
 */
public class IllegalConstructorArgs {

    public static void main(String[] args) {
        int testSucceeded=0;

        try {
            // this should generate an IllegalArgumentException
            Vector bad1 = new Vector(-100, 10);
        }
        catch (IllegalArgumentException e1) {
            testSucceeded =1;
        }
        catch (NegativeArraySizeException e2) {
            testSucceeded =0;
        }

        if (testSucceeded == 0)
            throw new RuntimeException("Wrong exception thrown.");

     }

}
