/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.image;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.netbeans.jemmy.util.PNGEncoder;

/**
 * Allowes to process PNF image format.
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public class PNGImageSaver implements ImageSaver {

    /**
     * Saves an image into a PNG image file.
     */
    @Override
    public void save(BufferedImage image, String fileName) throws IOException {
        new PNGEncoder(new BufferedOutputStream(new FileOutputStream(fileName)),
                PNGEncoder.COLOR_MODE).
                encode(image);
    }
}
