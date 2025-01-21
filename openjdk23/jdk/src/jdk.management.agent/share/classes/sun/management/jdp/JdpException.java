/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package sun.management.jdp;

/**
 * An Exception thrown if a JDP implementation encounters a problem.
 */
public final class JdpException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Construct a new JDP exception with a meaningful message
     *
     * @param msg - message
     */
    public JdpException(String msg) {
        super(msg);
    }
}
