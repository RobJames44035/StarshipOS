/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xs;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

/**
 * Describes a multi-value constraining facets: pattern and enumeration.
 */
public interface XSMultiValueFacet extends XSObject {
    /**
     * The name of the facet, i.e. <code>FACET_ENUMERATION</code> and
     * <code>FACET_PATTERN</code> (see <code>XSSimpleTypeDefinition</code>).
     */
    public short getFacetKind();

    /**
     * Values of this facet.
     */
    public StringList getLexicalFacetValues();

    /**
     * A list of XSValue objects. The actual enumeration values.
     */
    public ObjectList getEnumerationValues();

    /**
     * A sequence of [annotations] or an empty <code>XSObjectList</code>.
     */
    public XSObjectList getAnnotations();

}
