/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug     8190997
 * @summary Test verifies that ImageIO.read() throws proper IIOException
 *          when we have a PNG image with color type PNG_COLOR_PALETTE but
 *          missing the required PLTE chunk.
 * @run     main PngPLTEChunkMissingTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class PngPLTEChunkMissingTest {

    // PNG image stream missing the required PLTE chunk
    private static String inputImageBase64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAAB"
            + "CAMAAAA6fptVAAAACklEQVQYV2P4DwABAQEAWk1v8QAAAABJRU5ErkJgggo=";

    public static void main(String[] args) throws Exception {

        byte[] inputBytes = Base64.getDecoder().decode(inputImageBase64);
        InputStream in = new ByteArrayInputStream(inputBytes);

        /*
         * Attempt to read a PNG image of color type PNG_COLOR_PALETTE
         * but missing the required PLTE chunk.
         */
        try {
            ImageIO.read(in);
        } catch (IIOException e) {
            /*
             * We expect ImageIO to throw IIOException with proper message
             * instead of throwing NullPointerException.
             */
            Throwable cause = e.getCause();
            if (cause == null ||
                (!(cause.getMessage().
                 equals("Required PLTE chunk missing"))))
            {
                throw e;
            }
        }
    }
}

