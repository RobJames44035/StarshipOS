/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4090383
   @summary Ensure that BufferedInputStream's read method will fill the target
            array whenever possible
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;


public class Fill {

    /**
     * A simple InputStream that is always ready but may read fewer than the
     * requested number of bytes
     */
    static class Source extends InputStream {

        int shortFall;
        byte next = 0;

        Source(int shortFall) {
            this.shortFall = shortFall;
        }

        public int read() throws IOException {
            return next++;
        }

        public int read(byte[] buf, int off, int len) throws IOException {
            int n = len - shortFall;
            for (int i = off; i < n; i++)
                buf[i] = next++;
            return n;
        }

        public int available() {
            return Integer.MAX_VALUE;
        }

        public void close() throws IOException {
        }

    }

    /**
     * Test BufferedInputStream with an underlying source that always reads
     * shortFall fewer bytes than requested
     */
    static void go(int shortFall) throws Exception {

        InputStream r = new BufferedInputStream(new Source(shortFall), 10);
        byte[] buf = new byte[8];

        int n1 = r.read(buf);
        int n2 = r.read(buf);
        System.err.println("Shortfall " + shortFall
                           + ": Read " + n1 + ", then " + n2 + " bytes");
        if (n1 != buf.length)
            throw new Exception("First read returned " + n1);
        if (n2 != buf.length)
            throw new Exception("Second read returned " + n2);

    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 8; i++) go(i);
    }

}
