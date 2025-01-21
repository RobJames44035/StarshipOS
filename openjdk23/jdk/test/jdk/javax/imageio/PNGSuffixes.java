/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4394924
 * @summary Checks for spurious leading "." in PNG file suffixes
 * @modules java.desktop/com.sun.imageio.plugins.png
 */

import com.sun.imageio.plugins.png.PNGImageWriterSpi;

public class PNGSuffixes {

    public static void main(String[] args) {
        String[] suffixes = new PNGImageWriterSpi().getFileSuffixes();
        for (int i = 0; i < suffixes.length; i++) {
            if (suffixes[i].startsWith(".")) {
                throw new RuntimeException("Found a \".\" in a suffix!");
            }
        }
    }
}
