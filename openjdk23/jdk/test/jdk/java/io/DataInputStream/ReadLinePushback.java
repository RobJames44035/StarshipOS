/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4093646
   @summary Make sure readLine would not push back -1.
   */

import java.io.*;

public class ReadLinePushback {

    public static void main(String args[]) throws Exception {
        PushbackInputStream pis = new PushbackInputStream
            ((new StringBufferInputStream("\r")));
        DataInputStream dis = new DataInputStream(pis);

        String line = dis.readLine();
        if (line == null) {
            throw new Exception ("Got null, should return empty line");
        }

        int count = pis.available();

        if (count != 0) {
            throw new Exception ("Test failed: available() returns "
                                 + count + "when the file is empty");
        }
    }
}
