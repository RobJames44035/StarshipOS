/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.c14n.implementations;

import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;

/**
 */
public class Canonicalizer20010315OmitComments extends Canonicalizer20010315 {

    /**
     * Constructor Canonicalizer20010315WithXPathOmitComments
     *
     */
    public Canonicalizer20010315OmitComments() {
        super(false);
    }

    /** {@inheritDoc} */
    @Override
    public final String engineGetURI() {
        return Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS;
    }

}
