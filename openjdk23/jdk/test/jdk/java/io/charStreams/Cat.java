/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
   @summary Simple stream-copy test for InputStreamReader & OutputStreamWriter
 */

import java.io.*;

public class Cat {

    public static int count = 10000;

    public static void main(String[] args) throws IOException {
        Reader in = new InputStreamReader(new ABCInputStream(count));
        Writer out = new OutputStreamWriter(new ABCOutputStream(count));
        int c;

        while ((c = in.read()) != -1) {
            out.write(c);
            System.err.print((char)c);
        }
        in.close();
        out.close();
        System.err.println();
    }

}
