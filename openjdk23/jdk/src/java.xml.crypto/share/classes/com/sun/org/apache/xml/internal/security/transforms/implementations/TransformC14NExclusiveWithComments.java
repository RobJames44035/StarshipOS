/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.transforms.implementations;

import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315Excl;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315ExclWithComments;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;

/**
 * Implements the {@code http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments}
 * transform.
 *
 */
public class TransformC14NExclusiveWithComments extends TransformC14NExclusive {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String engineGetURI() {
        return Transforms.TRANSFORM_C14N_EXCL_WITH_COMMENTS;
    }

    @Override
    protected Canonicalizer20010315Excl getCanonicalizer() {
        return new Canonicalizer20010315ExclWithComments();
    }

}
