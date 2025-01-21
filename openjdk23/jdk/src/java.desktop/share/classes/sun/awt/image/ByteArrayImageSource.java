/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package sun.awt.image;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class ByteArrayImageSource extends InputStreamImageSource {
    byte[] imagedata;
    int imageoffset;
    int imagelength;

    public ByteArrayImageSource(byte[] data) {
        this(data, 0, data.length);
    }

    public ByteArrayImageSource(byte[] data, int offset, int length) {
        imagedata = data;
        imageoffset = offset;
        imagelength = length;
    }

    protected ImageDecoder getDecoder() {
        InputStream is = new ByteArrayInputStream(imagedata, imageoffset,
                imagelength);
        return getDecoder(is);
    }
}
