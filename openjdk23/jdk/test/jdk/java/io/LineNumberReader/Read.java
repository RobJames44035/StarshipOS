/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4074875 4063511 8235792
   @summary Make sure LineNumberReader.read(char, int , int) will increase
            the linenumber correctly.
   */

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.function.Consumer;

public class Read {

    public static void main(String[] args) throws Exception {
        testReadChars();
        testEofs();
    }

    private static void testReadChars() throws Exception {
        String s = "aaaa\nbbb\n";
        char[] buf = new char[5];
        int n = 0;
        int line = 0;

        LineNumberReader r = new LineNumberReader(new StringReader(s));

        do {
            n = r.read(buf, 0, buf.length);
        } while (n > 0);

        line = r.getLineNumber();

        if (line != 2)
            throw new Exception("Failed test: Expected line number: 2, got "
                                + line);
    }

    private static void testEofs() throws Exception {
        String string = "first \n second";

        Consumer<LineNumberReader> c = (LineNumberReader r) -> {
            try {
                while (r.read() != -1)
                    continue;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        testEof(c, string, 2);

        c = (LineNumberReader r) -> {
            try {
                char[] buf = new char[128];
                while (r.read(buf) != -1)
                    continue;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        testEof(c, string, 2);

        c = (LineNumberReader r) -> {
            try {
                while (r.readLine() != null)
                    continue;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        testEof(c, string, 2);
    }

    private static void testEof(Consumer<LineNumberReader> c, String s, int n)
        throws Exception {
        LineNumberReader r = new LineNumberReader(new StringReader(s));
        c.accept(r);
        int line;
        if ((line = r.getLineNumber()) != n)
            throw new Exception("Failed test: Expected line number: " + n  +
                " , got " + line);
    }
}
