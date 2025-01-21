/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6284538
 * @summary Test verifies whether IllegalStateException is thrown if the output
 *          stream have not set to the GIF image writer instance
 */

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;

public class PrepareWriteSequenceTest {
    public static void main(String[] args) throws IOException {
        String format = "GIF";
        ImageWriter writer = ImageIO.getImageWritersByFormatName(format).next();

        ImageWriteParam param = writer.getDefaultWriteParam();

        IIOMetadata streamMetadata = writer.getDefaultStreamMetadata(param);

        boolean gotException = false;
        try {
            writer.prepareWriteSequence(streamMetadata);
        } catch (IllegalStateException e) {
            gotException = true;
            System.out.println("Test passed.");
            e.printStackTrace(System.out);
        }

        if (!gotException) {
            throw new RuntimeException("Test failed.");
        }
    }
}
