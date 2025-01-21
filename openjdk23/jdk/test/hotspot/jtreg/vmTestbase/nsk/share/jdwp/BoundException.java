/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.share.jdwp;

/**
 * This exception is thrown when ByteBuffer parse errors are encountered.
 */
public class BoundException extends Exception {
    public BoundException(String message) {
        super(message);
    }
}
