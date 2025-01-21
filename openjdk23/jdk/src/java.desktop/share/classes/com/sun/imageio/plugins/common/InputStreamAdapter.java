/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package com.sun.imageio.plugins.common;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.stream.ImageInputStream;

public class InputStreamAdapter extends InputStream {

    ImageInputStream stream;

    public InputStreamAdapter(ImageInputStream stream) {
        super();

        this.stream = stream;
    }

    public int read() throws IOException {
        return stream.read();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return stream.read(b, off, len);
    }
}
