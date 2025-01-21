/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xs;

/**
 * Describes a constraining facet. Enumeration and pattern facets are exposed
 * via <code>XSMultiValueFacet</code> interface.
 */
public interface XSFacet extends XSObject {
    /**
     * The name of the facet, e.g. <code>FACET_LENGTH, FACET_TOTALDIGITS</code>
     *  (see <code>XSSimpleTypeDefinition</code>).
     */
    public short getFacetKind();

    /**
     * A value of this facet.
     */
    public String getLexicalFacetValue();

    /**
     * If this facet is length, minLength, maxLength, totalDigits, or
     * fractionDigits, and if the value can fit in "int", then return the value
     * of the facet as an int. If the value can't fit, return -1. Use
     * getActualFacetValue() to get the BigInteger representation. For all other
     * facets, return 0.
     */
    public int getIntFacetValue();

    /**
     * If this facet is minInclusive, maxInclusive, minExclusive, or
     * maxExclusive, then return the actual value of the facet. If this facet
     * is length, minLength, maxLength, totalDigits, or fractionDigits, then
     * return a BigInteger representation of the value. If this facet is
     * whiteSpace, then return the String representation of the facet.
     */
    public Object getActualFacetValue();

    /**
     * [Facets]: check whether a facet is fixed.
     */
    public boolean getFixed();

    /**
     * An annotation if it exists, otherwise <code>null</code>. If not null
     * then the first [annotation] from the sequence of annotations.
     */
    public XSAnnotation getAnnotation();

    /**
     * A sequence of [annotations] or an empty <code>XSObjectList</code>.
     */
    public XSObjectList getAnnotations();
}
