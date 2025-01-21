/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4101576
   @summary Test if read(byte[], int, int) works correctly.
*/

import java.io.*;

public class ReadToArray {
    public static void main(String[] args) throws Exception {
        PipedWriter pw = new PipedWriter();
        PipedReader pr = new PipedReader(pw);
        char[] cbuf = {'a', 'a', 'a', 'a'};

        pw.write('b');
        // read 'b' and put it to position 2
        pr.read(cbuf, 2, 1);

        if (cbuf[2] != 'b') {
            throw new Exception
            ("Read character to wrong position: 2nd character should be b");
        }
    }
}
