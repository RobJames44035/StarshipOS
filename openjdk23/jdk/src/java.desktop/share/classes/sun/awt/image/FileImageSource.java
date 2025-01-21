/*
 * StarshipOS Copyright (c) 1995-2025. R.A. James
 */

package sun.awt.image;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;

public class FileImageSource extends InputStreamImageSource {
    String imagefile;

    public FileImageSource(String filename) {
        imagefile = filename;
    }

    protected ImageDecoder getDecoder() {
        if (imagefile == null) {
            return null;
        }

        InputStream is;
        try {
            is = new BufferedInputStream(new FileInputStream(imagefile));
        } catch (FileNotFoundException e) {
            return null;
        }
        // Don't believe the file suffix - many users don't know what
        // kind of image they have and guess wrong...
        /*
        int suffixpos = imagefile.lastIndexOf('.');
        if (suffixpos >= 0) {
            String suffix = imagefile.substring(suffixpos+1).toLowerCase();
            if (suffix.equals("gif")) {
                return new GifImageDecoder(this, is);
            } else if (suffix.equals("jpeg") || suffix.equals("jpg") ||
                       suffix.equals("jpe") || suffix.equals("jfif")) {
                return new JPEGImageDecoder(this, is);
            } else if (suffix.equals("xbm")) {
                return new XbmImageDecoder(this, is);
            }
        }
        */
        return getDecoder(is);
    }
}
