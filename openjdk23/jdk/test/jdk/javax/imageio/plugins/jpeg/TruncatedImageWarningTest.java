/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug     8032370
 *
 * @summary Test verifies that Image I/O jpeg reader correctly handles
 *          and warns of a truncated image stream.
 *
 * @run     main TruncatedImageWarningTest
 */

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.event.IIOReadWarningListener;
import javax.imageio.stream.ImageInputStream;

public class TruncatedImageWarningTest implements IIOReadWarningListener {

    private static String fileName = "truncated.jpg";
    boolean receivedWarning = false;

    public static void main(String[] args) throws IOException {

        String sep = System.getProperty("file.separator");
        String dir = System.getProperty("test.src", ".");
        String filePath = dir+sep+fileName;
        System.out.println("Test file: " + filePath);
        File f = new File(filePath);
        ImageInputStream in = ImageIO.createImageInputStream(f);
        ImageReader reader = ImageIO.getImageReaders(in).next();
        TruncatedImageWarningTest twt = new TruncatedImageWarningTest();
        reader.addIIOReadWarningListener(twt);
        reader.setInput(in);
        reader.read(0);
        if (!twt.receivedWarning) {
            throw new RuntimeException("No expected warning");
        }
    }

    public void warningOccurred(ImageReader source, String warning) {
        System.out.println("Expected warning: " + warning);
        receivedWarning = true;
    }
}
