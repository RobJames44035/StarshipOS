/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */
package com.sun.imageio.plugins.tiff;

import javax.imageio.plugins.tiff.BaselineTIFFTagSet;
import javax.imageio.ImageWriteParam;

/**
 * Compressor for Deflate compression.
 */
public class TIFFDeflateCompressor extends TIFFDeflater {
    public TIFFDeflateCompressor(ImageWriteParam param, int predictor) {
        super("Deflate", BaselineTIFFTagSet.COMPRESSION_DEFLATE, param,
              predictor);
    }
}
