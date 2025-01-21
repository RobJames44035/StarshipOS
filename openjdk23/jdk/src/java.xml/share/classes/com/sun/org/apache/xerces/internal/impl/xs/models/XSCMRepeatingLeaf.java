/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.xs.models;

/**
 * A compound content model leaf node which carries occurence information.
 *
 * @xerces.internal
 *
 * @author Michael Glavassevich, IBM
 */
public final class XSCMRepeatingLeaf extends XSCMLeaf {

    private final int fMinOccurs;
    private final int fMaxOccurs;

    public XSCMRepeatingLeaf(int type, Object leaf,
            int minOccurs, int maxOccurs, int id, int position) {
        super(type, leaf, id, position);
        fMinOccurs = minOccurs;
        fMaxOccurs = maxOccurs;
    }

    final int getMinOccurs() {
        return fMinOccurs;
    }

    final int getMaxOccurs() {
        return fMaxOccurs;
    }
}
