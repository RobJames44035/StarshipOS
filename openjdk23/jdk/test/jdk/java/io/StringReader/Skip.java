/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4175312
 * @summary Test StringReader.skip with negative param
 */

import java.io.*;

public class Skip {
    public static void main( String argv[] ) throws Exception {
        StringReader in = new StringReader("1234567");

        // Skip forward and read
        if (in.skip(3) != 3)
            throw new RuntimeException("skip(3) failed");
        if (in.read() != '4')
            throw new RuntimeException("post skip read failure");

        // Skip backward and read
        if (in.skip(-2) != -2)
            throw new RuntimeException("skip(-2) failed");
        if (in.read() != '3')
            throw new RuntimeException("read failed after negative skip");

        // Attempt to skip backward past the beginning and read
        if (in.skip(-6) != -3)
            throw new RuntimeException("skip(-6) failed");
        if (in.read() != '1')
            throw new RuntimeException("read after skip past beginning failed");

        // Skip beyond the end
        if (in.skip(30) != 6)
            throw new RuntimeException("skip(30) failed");
        if (in.read() != -1)
            throw new RuntimeException("read at EOF failed");

        // Test after reaching end of string
        if (in.skip(30) != 0)
            throw new RuntimeException("skip(30) failed");
        if (in.skip(-30) != 0)
            throw new RuntimeException("skip(30) failed");
    }
}
