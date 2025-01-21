/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.jdi.request;

/**
 * Thrown to indicate a duplicate event request.
 *
 * @author Robert Field
 * @since  1.3
 */
public class DuplicateRequestException extends RuntimeException {

    private static final long serialVersionUID = -3719784920313411060L;

    public DuplicateRequestException() {
        super();
    }

    public DuplicateRequestException(String s) {
        super(s);
    }
}
