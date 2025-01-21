/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 * @test
 * @bug 4244408
 * @summary toString() should be called implicitly when += is used.
 *
 * @run main ImplicitToString
 */


// The failure for this test is a ClassCastException when the code is run.

import java.util.ArrayList;

public class ImplicitToString {
    public static void main(String[] args) {
        String s = "";
        ArrayList al = new ArrayList();
        al.add(new Object());

        // In the following line, toString() should be implicitly called.
        s += al.get(0);
    }
}
