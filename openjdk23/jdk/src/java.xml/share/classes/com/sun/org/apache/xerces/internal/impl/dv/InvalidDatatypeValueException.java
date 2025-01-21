/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.dv;

/**
 * Datatype exception for invalid values.
 *
 * @xerces.internal
 *
 * @author Sandy Gao, IBM
 *
 */
public class InvalidDatatypeValueException extends DatatypeException {

    /** Serialization version. */
    static final long serialVersionUID = -5523739426958236125L;

    /**
     * Create a new datatype exception by providing an error code and a list
     * of error message substitution arguments.
     *
     * @param key  error code
     * @param args error arguments
     */
    public InvalidDatatypeValueException(String key, Object[] args) {
        super(key, args);
    }

}
