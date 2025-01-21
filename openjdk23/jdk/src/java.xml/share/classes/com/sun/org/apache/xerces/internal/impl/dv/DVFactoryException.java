/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.dv;

/**
 * A runtime exception that's thrown if an error happens when the application
 * tries to get a DV factory instance.
 *
 * @xerces.internal
 *
 */
public class DVFactoryException extends RuntimeException {

    /** Serialization version. */
    static final long serialVersionUID = -3738854697928682412L;

    public DVFactoryException() {
        super();
    }

    public DVFactoryException(String msg) {
        super(msg);
    }
}
