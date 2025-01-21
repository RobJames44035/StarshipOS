/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.transforms.implementations;

import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_WithComments;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;

/**
 * Implements the {@code http://www.w3.org/2006/12/xml-c14n-11#WithComments}
 * (C14N 1.1 With Comments) transform.
 *
 */
public class TransformC14N11_WithComments extends TransformC14N {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String engineGetURI() {
        return Transforms.TRANSFORM_C14N11_WITH_COMMENTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Canonicalizer20010315 getCanonicalizer() {
        return new Canonicalizer11_WithComments();
    }
}
