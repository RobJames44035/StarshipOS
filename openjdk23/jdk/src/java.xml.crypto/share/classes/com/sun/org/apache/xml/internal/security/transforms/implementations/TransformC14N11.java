/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.transforms.implementations;

import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_OmitComments;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;

/**
 * Implements the {@code http://www.w3.org/2006/12/xml-c14n11}
 * (C14N 1.1) transform.
 */
public class TransformC14N11 extends TransformC14N {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String engineGetURI() {
        return Transforms.TRANSFORM_C14N11_OMIT_COMMENTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Canonicalizer20010315 getCanonicalizer() {
        return new Canonicalizer11_OmitComments();
    }

}
