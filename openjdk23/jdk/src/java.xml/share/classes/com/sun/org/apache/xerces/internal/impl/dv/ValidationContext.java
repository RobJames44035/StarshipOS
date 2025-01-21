/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.dv;

import java.util.Locale;

/**
 * ValidationContext has all the information required for the
 * validation of: id, idref, entity, notation, qname
 *
 * @xerces.internal
 *
 * @author Sandy Gao, IBM
 */
public interface ValidationContext {
    // whether to validate against facets
    public boolean needFacetChecking();

    // whether to do extra id/idref/entity checking
    public boolean needExtraChecking();

    // whether we need to normalize the value that is passed!
    public boolean needToNormalize();

    // are namespaces relevant in this context?
    public boolean useNamespaces();

    // entity
    public boolean isEntityDeclared (String name);
    public boolean isEntityUnparsed (String name);

    // id
    public boolean isIdDeclared (String name);
    public void    addId(String name);

    // idref
    public void addIdRef(String name);

    // get symbol from symbol table
    public String getSymbol (String symbol);

    // qname
    public String getURI(String prefix);

    // Locale
    public Locale getLocale();

}
