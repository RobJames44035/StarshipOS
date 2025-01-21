/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug     8211422
 * @summary Test verifies that PNGImageReader does not throw
 *          IIOException when IEND chunk has corrupt CRC chunk.
 * @run     main PNGCorruptCRCForIENDChunkTest
 */

import java.io.ByteArrayInputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.util.Iterator;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;

public class PNGCorruptCRCForIENDChunkTest {

    // PNG image stream having corrupt CRC for IEND chunk
    private static String inputImageBase64 = "iVBORw0KGgoAAAANSUhEUgAAAA" +
            "8AAAAQCAYAAADJViUEAAAAIElEQVR4XmNQllf4Ty5mABHkgFHNJIJRzSSCo" +
            "a6ZXAwA26ElUIYphtYAAAAASUVORK5C";

    public static void main(String[] args) throws Exception {

        byte[] inputBytes = Base64.getDecoder().decode(inputImageBase64);
        ByteArrayInputStream bais = new ByteArrayInputStream(inputBytes);
        ImageInputStream input = ImageIO.createImageInputStream(bais);
        Iterator iter = ImageIO.getImageReaders(input);
        ImageReader reader = (ImageReader) iter.next();
        reader.setInput(input, false, false);
        BufferedImage image = reader.read(0, reader.getDefaultReadParam());
    }
}
