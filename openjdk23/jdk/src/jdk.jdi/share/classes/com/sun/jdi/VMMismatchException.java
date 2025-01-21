/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package com.sun.jdi;

/**
 * Thrown to indicate that the requested operation cannot be
 * completed because the mirror from one target VM is being
 * combined with a mirror from another target VM.
 *
 * @author Gordon Hirsch
 * @since  1.3
 */
public class VMMismatchException extends RuntimeException {

    private static final long serialVersionUID = 289169358790459564L;

    public VMMismatchException() {
        super();
    }

    public VMMismatchException(String s) {
        super(s);
    }
}
