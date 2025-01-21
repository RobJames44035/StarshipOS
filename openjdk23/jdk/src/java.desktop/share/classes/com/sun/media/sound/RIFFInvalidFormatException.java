/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package com.sun.media.sound;

import java.io.Serial;

/**
 * This exception is used when a reader is used to read RIFF file of a format it
 * doesn't unterstand or support.
 *
 * @author Karl Helgason
 */
public final class RIFFInvalidFormatException extends InvalidFormatException {

    /**
     * Use serialVersionUID from JDK 1.7 for interoperability.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public RIFFInvalidFormatException() {
        super("Invalid format!");
    }

    public RIFFInvalidFormatException(String s) {
        super(s);
    }
}
