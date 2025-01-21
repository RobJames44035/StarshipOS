/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4414455
 * @summary Checks for NPE from ImageWriter.setOutput when the writer has no
 *          originating service provider
 * @modules java.desktop/com.sun.imageio.plugins.png
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import com.sun.imageio.plugins.png.PNGImageWriter;

public class SetOutput {

    public static void main(String[] args) throws IOException {
        ImageWriter iw = new PNGImageWriter(null);
        File f = File.createTempFile("imageio", "tmp");
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(f)) {
            iw.setOutput(ios);
        } finally {
            Files.delete(f.toPath());
        }
    }
}
