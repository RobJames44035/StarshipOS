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
public class Canonicalizer11_WithComments extends Canonicalizer20010315 {

    public Canonicalizer11_WithComments() {
        super(true, true);
    }

    @Override
    public final String engineGetURI() {
        return Canonicalizer.ALGO_ID_C14N11_WITH_COMMENTS;
    }

}
