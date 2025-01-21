/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Interface for all classes performing image loading.
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public interface ImageLoader {

    /**
     * Loads an image from file.
     *
     * @param fileName a file to load image from.
     * @return a loaded image.
     * @throws IOException
     */
    public BufferedImage load(String fileName) throws IOException;
}
