/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4103987
   @summary Tests LineNumberReader to see if the lineNumber is set correctly
            when mixed reads and readLines are used.
   */

import java.io.*;

public class ReadReadLine {

    public static void main(String[] args) throws Exception {
        test("\r\n", 1);
        test("\r\r\n", 2);
        test("\r\n\n\n", 3);
    }

    static void test(String s, int good) throws Exception {
        int c, line;

        LineNumberReader r = new LineNumberReader(new StringReader(s), 2);
        if ((c = r.read()) >= 0) {
            while (r.readLine() != null)
                ;
        }

        line = r.getLineNumber();

        if(line != good) {
            throw new Exception("Failed test: Expected line number "
                                + good + " Got: " + line);
        }
    }
}
