/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4090383
   @summary Ensure that BufferedReader's read method will fill the target array
            whenever possible
 */


import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;


public class Fill {

    /**
     * A simple Reader that is always ready but may read fewer than the
     * requested number of characters
     */
    static class Source extends Reader {

        int shortFall;
        char next = 0;

        Source(int shortFall) {
            this.shortFall = shortFall;
        }

        public int read(char[] cbuf, int off, int len) throws IOException {
            int n = len - shortFall;
            for (int i = off; i < n; i++)
                cbuf[i] = next++;
            return n;
        }

        public boolean ready() {
            return true;
        }

        public void close() throws IOException {
        }

    }

    /**
     * Test BufferedReader with an underlying source that always reads
     * shortFall fewer characters than requested
     */
    static void go(int shortFall) throws Exception {

        Reader r = new BufferedReader(new Source(shortFall), 10);
        char[] cbuf = new char[8];

        int n1 = r.read(cbuf);
        int n2 = r.read(cbuf);
        System.err.println("Shortfall " + shortFall
                           + ": Read " + n1 + ", then " + n2 + " chars");
        if (n1 != cbuf.length)
            throw new Exception("First read returned " + n1);
        if (n2 != cbuf.length)
            throw new Exception("Second read returned " + n2);

    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 8; i++) go(i);
    }

}
