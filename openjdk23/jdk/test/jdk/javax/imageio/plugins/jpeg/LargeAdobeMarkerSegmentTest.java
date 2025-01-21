/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug     6355567
 * @summary Verifies that AdobeMarkerSegment() keeps the available bytes
 *          and buffer pointer in sync, when a non-standard length Adobe
 *          marker is encountered.
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class LargeAdobeMarkerSegmentTest {

    private static String fileName = "jdk_6355567.jpg";

    public static void main(String[] args) throws IOException {
      /*
       * Open a JPEG image, and get the metadata. Without the fix for
       * 6355567, a NegativeArraySizeException is thrown while reading
       * the metadata from the JPEG below.
       */
      String sep = System.getProperty("file.separator");
      String dir = System.getProperty("test.src", ".");
      String filePath = dir+sep+fileName;
      System.out.println("Test file: " + filePath);
      File f = new File(filePath);
      ImageInputStream iis = ImageIO.createImageInputStream(f);
      ImageReader r = ImageIO.getImageReaders(iis).next();
      r.setInput(iis);
      r.getImageMetadata(0);
    }
}
