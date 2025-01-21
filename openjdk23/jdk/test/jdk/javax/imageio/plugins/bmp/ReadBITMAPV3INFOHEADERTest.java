/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug     7031957
 * @summary Test verifies whether BMPImageReader is capable of reading
 *          images with DIB header type BITMAPV3INFOHEADER.
 * @run     main ReadBITMAPV3INFOHEADERTest
 */

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ReadBITMAPV3INFOHEADERTest {
    public static void main(String[] args) throws IOException {
        String dir = System.getProperty("test.src");
        String sep = System.getProperty("file.separator");
        /*
         * Try reading BITMAPV3INFOHEADER type BMP images and check whether
         * read fails. If read fails we throw Exception.
         */
        ImageIO.read(new File(dir + sep + "DIB_size-56_ARGB_16bits.bmp"));
        ImageIO.read(new File(dir + sep + "DIB_size-56_RGB_16bits.bmp"));
        ImageIO.read(new File(dir + sep + "DIB_size-56_XRGB_32bits.bmp"));
    }
}

