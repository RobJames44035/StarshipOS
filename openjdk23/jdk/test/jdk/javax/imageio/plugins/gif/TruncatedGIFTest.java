/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 6532025
 * @summary Test verifies that we dont throw IOOBE for truncated
 *          GIF image
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.IIOException;

public class TruncatedGIFTest {
    public static void main(String[] args) throws IOException {
        // GIFF stream with no GCT/LCT but one truncated LZW
        // image block
        byte[] ba = new byte[] { (byte)0x47, (byte) 0x49,
                (byte)0x46, (byte)0x38, (byte)0x39, (byte)0x61,
                (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x2c,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x00,
                (byte)0x00, (byte)0x04, (byte)0x0A, (byte)0x00};

        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(ba));
        } catch (IIOException e) {
            // do nothing we expect IIOException but we should not
            // throw IndexOutOfBoundsException
            System.out.println(e.toString());
            System.out.println("Caught IIOException ignore it");
            System.out.println("Test passed");
        }
    }
}

