/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 1267258
 * @summary Check for IOException upon write on closed stream.
 *
 */

import java.io.*;

/**
 * This class tests to see if an IOException is thrown when
 * an attempt is made to write to a closed PipedOutputStream.
 */

public class ClosedWrite {

    public static void main(String[] argv) throws Exception {
        PipedOutputStream os = new PipedOutputStream();
        PipedInputStream is = new PipedInputStream();
        os.connect(is);
        os.close();
        try {
            os.write(10);
            throw new
                RuntimeException("No IOException upon write on closed Stream");
        } catch(IOException e) {
            System.err.println("Test passed: IOException thrown");
        }
    }
}
