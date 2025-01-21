/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xerces.internal.utils;

/**
 * A configuration error. This was an internal class in ObjectFactory previously
 *
 * @LastModified: Oct 2017
 */
public final class ConfigurationError extends Error {
    private static final long serialVersionUID = 8095902236393167968L;

    //
    // Data
    //

    /** Exception. */
    private Exception exception;

    //
    // Constructors
    //

    /**
     * Construct a new instance with the specified detail string and
     * exception.
     */
    ConfigurationError(String msg, Exception x) {
        super(msg);
        this.exception = x;
    } // <init>(String,Exception)

    //
    // methods
    //

    /** Returns the exception associated to this error. */
    public Exception getException() {
        return exception;
    } // getException():Exception

} // class ConfigurationError
