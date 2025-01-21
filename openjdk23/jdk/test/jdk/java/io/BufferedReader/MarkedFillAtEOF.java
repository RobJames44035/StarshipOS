/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4069687
   @summary Test if fill() will behave correctly at EOF
            when mark is set.
*/

import java.io.*;

public class MarkedFillAtEOF {

    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new StringReader("12"));
        int count = 0;

        r.read();
        r.mark(2);
        // trigger the call to fill()
        while (r.read() != -1);
        r.reset();

        // now should only read 1 character
        while (r.read() != -1) {
            count++;
        }
        if (count != 1) {
            throw new Exception("Expect 1 character, but got " + count);
        }
    }
}
