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
 * Class Canonicalizer20010315ExclWithComments
 */
public class Canonicalizer20010315ExclWithComments extends Canonicalizer20010315Excl {

    /**
     * Constructor Canonicalizer20010315ExclWithComments
     *
     */
    public Canonicalizer20010315ExclWithComments() {
        super(true);
    }

    /** {@inheritDoc} */
    @Override
    public final String engineGetURI() {
        return Canonicalizer.ALGO_ID_C14N_EXCL_WITH_COMMENTS;
    }

}
