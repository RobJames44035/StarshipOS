/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8278086
 * @summary Tests that writing invalid bits per pixel image in BMP
            throws IOException
 */

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BMPBitsPerPixelTest {

    public static void main(String[] args) {
        test(1, false);
        test(2, true);
        test(3, true);
        test(4, false);
        test(5, true);
        test(6, true);
        test(7, true);
        test(8, false);
    }

    public static void test(int bpp, boolean shouldThrowException) {
        int palettes = (int)Math.pow(2, bpp);
        byte[] r = new byte[palettes];
        byte[] g = new byte[palettes];
        byte[] b = new byte[palettes];
        boolean exceptionThrown = false;
        try {
            IndexColorModel cm = new IndexColorModel(bpp, palettes, r, g, b);
            int imageType = BufferedImage.TYPE_BYTE_BINARY;
            if (bpp > 4) {
                imageType = BufferedImage.TYPE_BYTE_INDEXED;
            }
            BufferedImage img = new
                BufferedImage(10, 10, imageType, (IndexColorModel)cm);
            File file = File.createTempFile("test", ".bmp", new File("."));
            file.deleteOnExit();
            ImageIO.write(img, "BMP", file);
        } catch (IOException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected exception: " + e);
        }

        if (shouldThrowException && !exceptionThrown) {
            throw new RuntimeException("IOException was not caught.");
        } else if (!shouldThrowException && exceptionThrown) {
            throw new RuntimeException("IOException should not be thrown.");
        } else {
            System.out.println("Test PASSED.");
        }
    }
}
