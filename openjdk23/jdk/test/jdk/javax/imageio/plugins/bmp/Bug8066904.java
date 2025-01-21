/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug     8066904
 * @summary Test verifies whether Bits per Pixel in BMP
 *          Header is corrupted or not
 * @run     main Bug8066904
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bug8066904 {

    public static void main(String[] args) throws IOException {
        // corrupted byte array with improper Bits per pixel in header
        byte[] corruptedBmp = { (byte) 0x42, (byte) 0x4d, (byte) 0x7e,
                (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x3e, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x28, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x64, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x64,
                (byte) 0x00, (byte) 0x40, (byte) 0x06, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff };

        /** if IOException is caught then test will
         * pass otherwise throws a different exception.
         */
        try {
            ImageIO.read(new ByteArrayInputStream(corruptedBmp));
        } catch(Exception ex) {
            if (!(ex instanceof IOException))
                throw ex;
        }
    }
}
