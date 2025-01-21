/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package com.sun.media.sound;

import java.io.IOException;
import java.io.Serial;

/**
 * This exception is used when a file contains illegal or unexpected data.
 *
 * @author Karl Helgason
 */
public class InvalidDataException extends IOException {

    /**
     * Use serialVersionUID from JDK 1.7 for interoperability.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidDataException() {
        super("Invalid Data!");
    }

    public InvalidDataException(String s) {
        super(s);
    }
}
