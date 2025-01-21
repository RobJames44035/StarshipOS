/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */
package com.sun.imageio.plugins.tiff;

import javax.imageio.metadata.IIOMetadataFormat;

public class TIFFAttrInfo {
    int valueType = IIOMetadataFormat.VALUE_ARBITRARY;
    int dataType;
    boolean isRequired = false;
    int listMinLength = 0;
    int listMaxLength = Integer.MAX_VALUE;

    public TIFFAttrInfo() { }
}
