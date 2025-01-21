/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8302151
 * @summary Tests that we should not try to calculate bitmap image
            size using bitmap file size when we have a color palette
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BMP1bppImageWithPaletteTest {

    public static void main(String[] args) throws IOException {
        // incomplete 1bpp BMP byte stream with color palette and
        // invalid file size data
        byte[] corruptedBmp = { (byte) 0x42, (byte) 0x4d, (byte) 0x0e,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x3e, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x28, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0xa2, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0xb4,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00,
            (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0xe0, (byte) 0x57, (byte) 0x07, (byte) 0x00,
            (byte) 0xc2, (byte) 0x1e, (byte) 0x00, (byte) 0x00, (byte) 0xc2,
            (byte) 0x1e, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
        };

        // We expect EOFException to be thrown
        try {
            ImageIO.read(new ByteArrayInputStream(corruptedBmp));
        } catch(Exception ex) {
            if (!(ex instanceof EOFException))
                throw ex;
        }
    }
}
