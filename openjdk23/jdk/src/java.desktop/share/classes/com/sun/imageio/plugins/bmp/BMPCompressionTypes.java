/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package com.sun.imageio.plugins.bmp;

public class BMPCompressionTypes {

    private static final String[] compressionTypeNames =
        {"BI_RGB", "BI_RLE8", "BI_RLE4", "BI_BITFIELDS", "BI_JPEG", "BI_PNG"};

    static int getType(String typeString) {
        for (int i = 0; i < compressionTypeNames.length; i++)
            if (compressionTypeNames[i].equals(typeString))
                return i;
        return 0;
    }

    static String getName(int type) {
        return compressionTypeNames[type];
    }

    public static String[] getCompressionTypes() {
        return compressionTypeNames.clone();
    }
}
