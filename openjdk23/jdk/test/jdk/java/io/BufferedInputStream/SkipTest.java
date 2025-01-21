/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test 1.1 98/01/12
 * @bug 4022294
 * @summary Test bufferedinputstream for data loss during skip
 *
 */

import java.io.*;
import java.util.*;

/**
 * This class tests to see if bufferinputstream can be reset
 * to recover data that was skipped over when the buffer did
 * not contain all the bytes to be skipped
 */
public class SkipTest {

    public static void main(String[] args) throws Exception {
        long skipped = 0;

        // Create a tiny buffered stream so it can be easily
        // set up to contain only some of the bytes to skip
        DataSupplier source = new DataSupplier();
        BufferedInputStream in = new BufferedInputStream(source, 4);

        // Set up data to be skipped and recovered
        // the skip must be longer than the buffer size
        in.mark(30);
        while (skipped < 15) {
            skipped += in.skip(15-skipped);
        }
        int nextint = in.read();
        in.reset();

        // Resume reading and see if data was lost
        nextint = in.read();

        if (nextint != 'a')
            throw new RuntimeException("BufferedInputStream skip lost data");
    }
}


class DataSupplier extends InputStream {

    private int aposition=0;

    public int read() {
        return 'x';
    }

    public long skip(long n) {
        aposition += (int) n;
        return n;
    }

    public static final byte[] buffer = {(byte)'a',(byte)'b',(byte)'c',
(byte)'d',(byte)'e',(byte)'f',(byte)'g',(byte)'h',(byte)'i',
(byte)'j',(byte)'k',(byte)'l',(byte)'m',(byte)'n',(byte)'o',
(byte)'p',(byte)'q',(byte)'r',(byte)'s',(byte)'t',(byte)'u',
(byte)'v',(byte)'w',(byte)'x',(byte)'y',(byte)'z'
                                         };

    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    public int read(byte b[], int off, int len) throws IOException {
        if (len > buffer.length) len = buffer.length;
        System.arraycopy(buffer, aposition, b, off, len);
        return len;
    }

}
