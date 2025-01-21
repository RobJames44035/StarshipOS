/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package java.util.jar;

/**
 * Signals that an error of some sort has occurred while reading from
 * or writing to a JAR file.
 *
 * @author  David Connelly
 * @since   1.2
 */
public class JarException extends java.util.zip.ZipException {
    @java.io.Serial
    private static final long serialVersionUID = 7159778400963954473L;

    /**
     * Constructs a JarException with no detail message.
     */
    public JarException() {
    }

    /**
     * Constructs a JarException with the specified detail message.
     * @param s the detail message
     */
    public JarException(String s) {
        super(s);
    }
}
