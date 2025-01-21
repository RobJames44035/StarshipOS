/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8010837 8011136
 * @summary Test if available returns correct value when skipping beyond
 *          the end of a file.
 * @author Dan Xu
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class NegativeAvailable {

    public static void main(String[] args) throws IOException {
        final int SIZE = 10;
        final int SKIP = 5;
        final int NEGATIVE_SKIP = -5;

        // Create a temporary file with size of 10 bytes.
        Path tmp = Files.createTempFile(null, null);
        try {
            try (BufferedWriter writer =
                         Files.newBufferedWriter(tmp, Charset.defaultCharset())) {
                for (int i = 0; i < SIZE; i++) {
                    writer.write('1');
                }
            }

            File tempFile = tmp.toFile();
            try (FileInputStream fis = new FileInputStream(tempFile)) {
                if (tempFile.length() != SIZE) {
                    throw new RuntimeException("unexpected file size = "
                            + tempFile.length());
                }
                long space = skipBytes(fis, SKIP, SIZE);
                space = skipBytes(fis, NEGATIVE_SKIP, space);
                space = skipBytes(fis, SKIP, space);
                space = skipBytes(fis, SKIP, space);
                space = skipBytes(fis, SKIP, space);
                space = skipBytes(fis, NEGATIVE_SKIP, space);
                space = skipBytes(fis, NEGATIVE_SKIP, space);
            }
        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    /**
     *  Skip toSkip number of bytes and return the remaining bytes of the file.
     */
    private static long skipBytes(FileInputStream fis, int toSkip, long space)
            throws IOException {
        long skip = fis.skip(toSkip);
        if (skip != toSkip) {
            throw new RuntimeException("skip() returns " + skip
                                       + " but expected " + toSkip);
        }
        long newSpace = space - toSkip;
        long remaining = newSpace > 0 ? newSpace : 0;
        int avail = fis.available();
        if (avail != remaining) {
            throw new RuntimeException("available() returns " + avail
                                       + " but expected " + remaining);
        }

        System.out.println("Skipped " + skip + " bytes "
                           + " available() returns " + avail);
        return newSpace;
    }
}
