/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.awt.X11;

/**
 * Signals that some Xlib routine failed.
 *
 * @since 1.5
 */
@SuppressWarnings("serial") // JDK-implementation class
public class XException extends RuntimeException {
    public XException() {
        super();
    }
    public XException(String message) {
        super(message);
    }
}
