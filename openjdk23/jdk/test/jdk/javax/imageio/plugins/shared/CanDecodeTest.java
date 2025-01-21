/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary Verifies that canDecode does not throw EOFException
 *          if the file has too few bytes.
 * @run     main CanDecodeTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

public class CanDecodeTest {

    private static final String[] FORMATS = {
        "WBMP", "BMP", "GIF", "PNG", "TIFF", "JPEG"
    };

    public static void main(String[] args) {
        for (String format : FORMATS) {
            ImageReader reader =
                    ImageIO.getImageReadersByFormatName(format).next();
            ImageReaderSpi spi = reader.getOriginatingProvider();

            for (int n=0; n<8; n++) {
                InputStream dataStream =
                        new ByteArrayInputStream(new byte[n]);
                try {
                    ImageInputStream iis =
                            ImageIO.createImageInputStream(dataStream);

                    if (spi.canDecodeInput(iis)) {
                        throw new RuntimeException("Test failed for " +
                                format + " format: shall not decode.");
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Test failed for " +
                            format + " format: " + e, e);
                }
            }
        }
    }
}
