/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4411955
 * @summary Checks that the JPEG writer does not throw an OutOfMemoryError from
 *          its reset() method
 * @modules java.desktop/com.sun.imageio.plugins.jpeg
 */

import javax.imageio.ImageWriter;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;

public class ResetOutOfMemory {

    public static void main(String args[]) {
        ImageWriter writer = new JPEGImageWriter(null);
        try {
            writer.reset();
        } catch (OutOfMemoryError e) {
            throw new RuntimeException("Got OutOfMemoryError!");
        }
    }
}
