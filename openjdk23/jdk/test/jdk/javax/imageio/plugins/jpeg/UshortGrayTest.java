/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4450894
 * @summary Tests if the JPEGImageWriter allows images with > 8-bit samples to
 *          be written. Also tests the JPEGImageWriterSpi.canEncodeImage()
 *          mechanism for this same behavior.
 */

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class UshortGrayTest {

    public static void main(String[] args) {
        Iterator iter;
        BufferedImage bi = new BufferedImage(10, 10,
                                             BufferedImage.TYPE_USHORT_GRAY);

        // Part 1: ensure that JPEGImageWriter throws an exception if it
        // encounters an image with 16-bit samples
        ImageWriter writer = null;
        iter = ImageIO.getImageWritersByFormatName("jpeg");
        if (iter.hasNext()) {
            writer = (ImageWriter)iter.next();
        } else {
            throw new RuntimeException("No JPEG reader found");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream ios = null;
        boolean exceptionThrown = false;

        try {
            ios = ImageIO.createImageOutputStream(baos);
        } catch (IOException ioe) {
            throw new RuntimeException("Could not create ImageOutputStream");
        }

        try {
            writer.setOutput(ios);
            writer.write(bi);
        } catch (IOException ioe) {
            exceptionThrown = true;
        }

        if (!exceptionThrown) {
            throw new RuntimeException("JPEG writer should not be able to " +
                                       "write USHORT_GRAY images");
        }

        // Part 2: ensure that JPEGImageWriterSpi.canEncodeImage() returns
        // false for images with 16-bit samples
        ImageTypeSpecifier its =
            ImageTypeSpecifier.createFromRenderedImage(bi);

        iter = ImageIO.getImageWriters(its, "jpeg");
        if (iter.hasNext()) {
            throw new RuntimeException("JPEG writer should not be available" +
                                       " for USHORT_GRAY images");
        }
    }
}
