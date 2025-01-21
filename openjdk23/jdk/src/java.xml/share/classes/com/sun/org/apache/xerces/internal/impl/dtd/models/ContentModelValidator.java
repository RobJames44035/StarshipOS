/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.dtd.models;

import com.sun.org.apache.xerces.internal.xni.QName;

/**
 */
public interface ContentModelValidator {

    //
    // Methods
    //

    /**
     * validate
     *
     * @param children
     * @param offset
     * @param length
     *
     * @return The value -1 if fully valid, else the 0 based index of the child
     *         that first failed. If the value returned is equal to the number
     *         of children, then the specified children are valid but additional
     *         content is required to reach a valid ending state.
     */
    public int validate(QName[] children, int offset, int length);

} // interface ContentModelValidator
